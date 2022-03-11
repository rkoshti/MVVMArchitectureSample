package com.rajesh.mvvmarchitecture.data.repository

import android.util.Log
import com.rajesh.mvvmarchitecture.common.Resource
import com.rajesh.mvvmarchitecture.data.remote.ApiManager
import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDetailDto
import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDto
import com.rajesh.mvvmarchitecture.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: ApiManager
) : CoinRepository {

    override suspend fun getCoins(): Resource<List<CoinDto>> {
        return try {
            val response = api.getCoins()
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun getCoinsById(coinId: String): Resource<CoinDetailDto> {
        return try {
            val response = api.getCoinById(coinId)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}