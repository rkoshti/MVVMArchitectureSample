package com.rajesh.mvvmarchitecture.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.rajesh.mvvmarchitecture.R
import com.rajesh.mvvmarchitecture.common.Status
import com.rajesh.mvvmarchitecture.databinding.FragmentDetailBinding
import com.rajesh.mvvmarchitecture.databinding.FragmentListBinding
import com.rajesh.mvvmarchitecture.domain.viewmodels.CoinViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment @Inject constructor():  Fragment(R.layout.fragment_detail) {

    lateinit var binding: FragmentDetailBinding
    private val viewModel: CoinViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        val coinId = arguments?.getString("coinId").toString()
        setUpObservers()

        lifecycleScope.launch{
            viewModel.coinDetailById(coinId)
        }
    }

    private fun setUpObservers() {

        viewModel.coinDetail.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->

                when (result.status) {
                    Status.SUCCESS -> {
                        val response = result.data
                        Log.e(ListFragment::class.java.name, "Response >>> $response")

                        binding.tvDetail.text = response?.description
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
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
















