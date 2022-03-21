package com.rajesh.mvvmarchitecture.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rajesh.mvvmarchitecture.common.Constant.BASE_URL
import com.rajesh.mvvmarchitecture.common.DataStoreManager
import com.rajesh.mvvmarchitecture.data.remote.ApiManager
import com.rajesh.mvvmarchitecture.data.repository.CoinRepositoryImpl
import com.rajesh.mvvmarchitecture.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCoinRepository(api: ApiManager): CoinRepository {
        return CoinRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): ApiManager {

        // Kotlin serializer convertor factory
       /* val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val converterFactory = json.asConverterFactory(contentType)*/

        return Retrofit.Builder()
           // .addConverterFactory(converterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(ApiManager::class.java)
    }

    @Provides
    @Singleton
    fun providesPreferenceDataStore(@ApplicationContext appContext: Context): DataStoreManager {
        return DataStoreManager(appContext)
    }

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().create()

}

















