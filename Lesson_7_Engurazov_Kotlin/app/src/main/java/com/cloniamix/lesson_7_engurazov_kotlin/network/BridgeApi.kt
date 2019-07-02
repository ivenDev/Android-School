package com.cloniamix.lesson_7_engurazov_kotlin.network

import com.cloniamix.lesson_7_engurazov_kotlin.pojo.Bridges
import io.reactivex.Single
import retrofit2.http.GET

interface BridgeApi {

    @GET("/api/v1/bridges")
    fun getBridges(): Single<Bridges>

}