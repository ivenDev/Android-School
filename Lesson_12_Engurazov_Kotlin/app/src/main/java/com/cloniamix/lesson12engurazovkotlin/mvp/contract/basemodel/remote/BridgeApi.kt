package com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.remote

import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.Bridge
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.BridgesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BridgeApi {

    @GET("/api/v1/bridges")
    fun getBridges(): Single<BridgesResponse>

    @GET("/api/v1/bridges/{id}")
    fun getBridgeInfoById(@Path("id") bridgeId: Int): Single<Bridge>

}