package com.cloniamix.lesson_7_engurazov_kotlin.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object BridgeApiService{
    private const val BASE_URL = "https://gdemost.handh.ru"

    val getClient: BridgeApi
        get() {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(BridgeApi::class.java)
        }
}
