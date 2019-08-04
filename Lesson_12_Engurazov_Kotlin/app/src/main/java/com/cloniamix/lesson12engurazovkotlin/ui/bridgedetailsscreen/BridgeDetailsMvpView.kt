package com.cloniamix.lesson12engurazovkotlin.ui.bridgedetailsscreen

import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.MvpView

interface BridgeDetailsMvpView : MvpView{
    fun showState(stateFlag: Int)
    fun showBridgeDetails(bridge: Bridge)
}