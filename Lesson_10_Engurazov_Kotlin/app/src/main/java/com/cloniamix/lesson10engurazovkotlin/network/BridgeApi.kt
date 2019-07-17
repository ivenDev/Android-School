package com.cloniamix.lesson10engurazovkotlin.network

import com.cloniamix.lesson10engurazovkotlin.pojo.Bridges
import io.reactivex.Single
import retrofit2.http.GET

interface BridgeApi {

    @GET("/api/v1/bridges")
    fun getBridges(): Single<Bridges>

}