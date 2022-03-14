package com.rajesh.mvvmarchitecture.domain.repository

import com.rajesh.mvvmarchitecture.common.Resource
import com.rajesh.mvvmarchitecture.domain.model.Coin
import com.rajesh.mvvmarchitecture.domain.model.CoinDetail


interface CoinRepository {

    suspend fun getCoins(): Resource<List<Coin>>

    suspend fun getCoinsById(coinId: String): Resource<CoinDetail>
}