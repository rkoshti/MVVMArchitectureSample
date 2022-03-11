package com.rajesh.mvvmarchitecture.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajesh.mvvmarchitecture.common.Event
import com.rajesh.mvvmarchitecture.common.Resource
import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDetailDto
import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDto
import com.rajesh.mvvmarchitecture.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val repository: CoinRepository
) : ViewModel() {


    private val _allCoins = MutableLiveData<Event<Resource<List<CoinDto>>>>()
    val allCoins : LiveData<Event<Resource<List<CoinDto>>>> = _allCoins

    private val _getCoinById = MutableLiveData<Event<Resource<CoinDetailDto>>>()
    val coinDetail : LiveData<Event<Resource<CoinDetailDto>>> = _getCoinById

    fun getAllCoins() {
        _allCoins.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.getCoins()
            _allCoins.value = Event(response)
        }
    }

    fun coinDetailById(coinId: String) {
        if(coinId.isEmpty()) {
            return
        }
        _getCoinById.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.getCoinsById(coinId)
            _getCoinById.value = Event(response)
        }
    }


}