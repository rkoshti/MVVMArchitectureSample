package com.rajesh.mvvmarchitecture.domain.repository

import com.rajesh.mvvmarchitecture.common.Resource
import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDetailDto
import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDto


interface CoinRepository {

    suspend fun getCoins(): Resource<List<CoinDto>>

    suspend fun getCoinsById(coinId: String): Resource<CoinDetailDto>
}