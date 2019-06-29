package com.cloniamix.lesson_7_engurazov_kotlin.network

import com.cloniamix.lesson_7_engurazov_kotlin.pojo.Bridges
import io.reactivex.Single
import retrofit2.http.GET

interface BridgeApi {

    @GET("/api/v1/bridges")
    fun getBridges(): Single<Bridges>

    /*companion object Factory {
        private const val BASE_URL = "https://gdemost.handh.ru"

        fun create(): BridgeApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(BridgeApi::class.java)
        }
    }*/
}