package com.rajesh.mvvmarchitecture.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rajesh.mvvmarchitecture.R
import com.rajesh.mvvmarchitecture.adapter.CoinAdapter
import com.rajesh.mvvmarchitecture.common.Status
import com.rajesh.mvvmarchitecture.databinding.FragmentListBinding
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        setupRecyclerView()
        subscribeToObservers()

        lifecycleScope.launch {
            viewModel.getAllCoins()
        }

        coinListAdapter.setOnItemClickLister {
            val bundle = bundleOf("coinId" to it)
           //findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment())
            findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }
    }

    private fun subscribeToObservers() {
        viewModel.allCoins.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        binding.rvList.visibility = View.VISIBLE
                        val listOfCoins = result.data
                      //  Log.e(ListFragment::class.java.name, "Response >>> $listOfCoins")
                        if (listOfCoins != null) {
                            coinListAdapter.coins = listOfCoins
                        }
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            binding.rootLayout,
                            result.message ?: "An unknown error occurred.",
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.rvList.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
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















