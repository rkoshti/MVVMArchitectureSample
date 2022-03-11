package com.rajesh.mvvmarchitecture.di

import com.rajesh.mvvmarchitecture.data.remote.ApiManager
import com.rajesh.mvvmarchitecture.common.Constant.BASE_URL
import com.rajesh.mvvmarchitecture.data.repository.CoinRepositoryImpl
import com.rajesh.mvvmarchitecture.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCoinRepository(api : ApiManager) : CoinRepository {
        return CoinRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun providesLoggingInterceptor() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): ApiManager {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(ApiManager::class.java)
    }
}

















