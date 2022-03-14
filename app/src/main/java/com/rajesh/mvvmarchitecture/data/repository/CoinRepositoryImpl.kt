package com.rajesh.mvvmarchitecture.data.repository

import android.util.Log
import com.rajesh.mvvmarchitecture.common.Resource
import com.rajesh.mvvmarchitecture.data.remote.ApiManager
import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDetailDto
import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDto
import com.rajesh.mvvmarchitecture.data.remote.dto.toCoin
import com.rajesh.mvvmarchitecture.data.remote.dto.toCoinDetail
import com.rajesh.mvvmarchitecture.domain.model.Coin
import com.rajesh.mvvmarchitecture.domain.model.CoinDetail
import com.rajesh.mvvmarchitecture.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: ApiManager
) : CoinRepository {

    override suspend fun getCoins(): Resource<List<Coin>> {
        return try {
            val response = api.getCoins()
            if(response.isSuccessful) {
                response.body()?.let { data ->
                    return@let Resource.Success(data.map { it.toCoin() })
                } ?: Resource.Error("An unknown error occured", null)
            } else {
                Resource.Error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.Error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun getCoinsById(coinId: String): Resource<CoinDetail> {
        return try {
            val response = api.getCoinById(coinId)
            if(response.isSuccessful) {
                response.body()?.let { data ->
                    return@let Resource.Success(data.toCoinDetail())
                } ?: Resource.Error("An unknown error occured", null)
            } else {
                Resource.Error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.Error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}