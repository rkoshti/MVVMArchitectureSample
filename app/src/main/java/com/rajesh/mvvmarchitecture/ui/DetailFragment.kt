package com.rajesh.mvvmarchitecture.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rajesh.mvvmarchitecture.R
import com.rajesh.mvvmarchitecture.common.Resource
import com.rajesh.mvvmarchitecture.databinding.FragmentDetailBinding
import com.rajesh.mvvmarchitecture.domain.model.Coin
import com.rajesh.mvvmarchitecture.domain.viewmodels.CoinViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@ExperimentalSerializationApi
@AndroidEntryPoint
class DetailFragment @Inject constructor() : Fragment(R.layout.fragment_detail) {

    lateinit var binding: FragmentDetailBinding
    private val viewModel: CoinViewModel by viewModels()

    @Inject
    lateinit var gson: Gson

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)


        // example code syntax

        /*

        val lollipop = PlatformInfo("Lollipop", 21)
        val json = Json.encodeToString(serializer<PlatformInfo>(), lollipop)
        println(json) // {"platformName":"Lollipop","apiLevel":21}

        val platforms: List<PlatformInfo> = listOf(...)
        val platformsSerializer = ListSerializer(PlatformInfo.serializer())

         */


        val coin = arguments?.getSerializable("coinId") as Coin
      //  Log.e(DetailFragment::class.java.name, "coinID >> ${Json.encodeToString(coin)}")
        Log.e(
            DetailFragment::class.java.name,
            "coinID >> ${Json.decodeFromString(Coin.serializer(), Json.encodeToString(coin))}"
        )

        //Log.e(DetailFragment::class.java.name, "coinID >> ${gson.toJson(coin)}")
        //val coinId = arguments?.getString("coinId").toString()
        setUpObservers()

        lifecycleScope.launch {
            viewModel.coinDetailById(coin.id)
        }
    }

    private fun setUpObservers() {

        viewModel.coinDetail.observe(viewLifecycleOwner) {
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
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val response = result.data
                        Log.e(ListFragment::class.java.name, "Response >>> $response")

                        binding.tvDetail.text = response?.description
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}
















