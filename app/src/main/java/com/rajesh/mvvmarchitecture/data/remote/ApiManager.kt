package com.rajesh.mvvmarchitecture.data.remote

import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDetailDto
import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiManager {

    @GET("/v1/coins")
    suspend fun getCoins() : Response<List<CoinDto>>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String) : Response<CoinDetailDto>

}