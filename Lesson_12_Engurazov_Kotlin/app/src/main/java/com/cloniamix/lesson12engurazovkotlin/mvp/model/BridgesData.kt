package com.cloniamix.lesson12engurazovkotlin.mvp.model

import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.Bridge

class BridgesData private constructor() {

    private var bridgesList: List<Bridge> = ArrayList()

    companion object {
        private var INSTANCE: BridgesData? = null

        fun getInstance(): BridgesData? {
            if (INSTANCE == null) {
                synchronized(BridgesData::class.java) {
                    INSTANCE =
                        BridgesData()
                }
            }
            return INSTANCE
        }
    }

    fun setBridgesList(bridgesList: List<Bridge>) {
        this.bridgesList = bridgesList
    }

    fun getBridgesList(): List<Bridge> {
        return bridgesList
    }
}