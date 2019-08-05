package com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview

import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.Bridge
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview.FragmentMvpView

interface BridgeDetailsMvpView : FragmentMvpView {
    fun showBridgeDetails(bridge: Bridge)
}