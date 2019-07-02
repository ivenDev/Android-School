package com.cloniamix.lesson_7_engurazov_kotlin.network

import com.cloniamix.lesson_7_engurazov_kotlin.Utils.Companion.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object BridgeApiService{

    val getClient: BridgeApi
        get() {
            val timeFormat = "HH:mm:ss"

            val gson = GsonBuilder()
                    .setDateFormat(timeFormat)
                    .create()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(BridgeApi::class.java)
        }
}
