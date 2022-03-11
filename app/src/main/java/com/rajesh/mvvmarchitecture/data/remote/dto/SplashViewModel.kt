package com.rajesh.mvvmarchitecture.data.remote.dto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _loading = MutableStateFlow(true)
    val isLoading = _loading.asStateFlow()

    init {
        viewModelScope.launch {
            //delay will work as a api request
            delay(3000)
            _loading.value = false
        }
    }
}