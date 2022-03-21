package com.rajesh.mvvmarchitecture.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rajesh.mvvmarchitecture.R
import com.rajesh.mvvmarchitecture.adapter.CoinAdapter
import com.rajesh.mvvmarchitecture.common.Constant
import com.rajesh.mvvmarchitecture.common.Constant.PARAM_COIN_ID
import com.rajesh.mvvmarchitecture.common.DataStoreManager
import com.rajesh.mvvmarchitecture.common.Resource
import com.rajesh.mvvmarchitecture.databinding.FragmentListBinding
import com.rajesh.mvvmarchitecture.domain.model.Coin
import com.rajesh.mvvmarchitecture.domain.viewmodels.CoinViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment @Inject constructor(
    private val coinListAdapter: CoinAdapter
) : Fragment(R.layout.fragment_list) {

    private val viewModel: CoinViewModel by viewModels()
    private lateinit var binding: FragmentListBinding

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @Inject lateinit var gson: Gson


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        setupRecyclerView()
        subscribeToObservers()

        lifecycleScope.launch {
            viewModel.getAllCoins()
        }

        coinListAdapter.setOnItemClickLister {

            val coin = Coin(it,true,"Rajesh bit coin",5,"raj-bitcoin")
           // storeAndReadDataFromDataStore(gson.toJson(coin))
            val bundle = bundleOf("coinId" to coin)
            //findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment())
            findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }
    }

    /**
     * Function for read and save data in data store
     */
    private fun storeAndReadDataFromDataStore(value: String) {
        lifecycleScope.launch {
            dataStoreManager.putString(PARAM_COIN_ID, value)
            val coin = gson.fromJson(dataStoreManager.getString(PARAM_COIN_ID),Coin::class.java)
            Log.e(ListFragment::class.java.name, "Preference Value is >>>>>  ${gson.toJson(coin)}")
        }
    }

    /* Flow collect example of data store
    private fun setupUI() {
        lifecycleScope.launch {
            dataStoreManager.themeMode.collectIn(this@MainActivity) { mode ->
                setNightMode(mode)
            }
        }
    }*/

    private fun subscribeToObservers() {
        viewModel.allCoins.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result) {
                    is Resource.Error -> {
                        Snackbar.make(
                            binding.rootLayout,
                            result.message ?: "An unknown error occurred.",
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.rvList.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.rvList.visibility = View.VISIBLE
                        val listOfCoins = result.data
                          Log.e(ListFragment::class.java.name, "Response >>> ${gson.toJson(listOfCoins)}")
                        if (listOfCoins != null) {
                            coinListAdapter.coins = listOfCoins
                        }
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvList.apply {
            adapter = coinListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
















