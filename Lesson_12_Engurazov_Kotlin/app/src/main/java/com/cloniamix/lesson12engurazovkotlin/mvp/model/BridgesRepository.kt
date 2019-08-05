package com.cloniamix.lesson12engurazovkotlin.mvp.model

import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.Bridge
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.BridgesResponse
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.remote.BridgeApi
import io.reactivex.Single

class BridgesRepository(private val apiService: BridgeApi) {

    fun getBridges(): Single<List<Bridge>> {
        return apiService.getBridges()
            .map(BridgesResponse::getBridges)
    }

    fun getBridgeInfoById(bridgeId: Int): Single<Bridge> {
        return apiService.getBridgeInfoById(bridgeId)
    }
}