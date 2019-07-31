package com.cloniamix.lesson12engurazovkotlin.provider

import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.data.model.BridgesResponse
import com.cloniamix.lesson12engurazovkotlin.data.remote.BridgeApi
import io.reactivex.Single

class BridgesRepository(private val apiService: BridgeApi) {

    fun getBridges(): Single<List<Bridge>>{
        return apiService.getBridges()
            .map(BridgesResponse::getBridges)
    }

    fun getBridgeInfoById(bridgeId: Int): Single<Bridge>{
        return apiService.getBridgeInfoById(bridgeId)
    }
}