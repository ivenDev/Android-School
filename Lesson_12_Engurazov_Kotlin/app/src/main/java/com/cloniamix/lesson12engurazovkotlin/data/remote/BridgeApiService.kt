package com.cloniamix.lesson12engurazovkotlin.data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object BridgeApiService {

    private const val BASE_URL = "https://gdemost.handh.ru"
    private const val TIME_FORMAT = "HH:mm:ss"
    val getClient: BridgeApi
        get() {


            val gson = GsonBuilder()
                .setDateFormat(TIME_FORMAT)
                .create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(BridgeApi::class.java)
        }
}