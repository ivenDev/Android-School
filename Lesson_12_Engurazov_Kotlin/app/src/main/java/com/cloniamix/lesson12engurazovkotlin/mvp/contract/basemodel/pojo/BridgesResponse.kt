package com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo

import com.google.gson.annotations.SerializedName


class BridgesResponse(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("objects") val bridges: ArrayList<Bridge>
){
    fun getBridges(): List<Bridge>{
        return bridges
    }
}