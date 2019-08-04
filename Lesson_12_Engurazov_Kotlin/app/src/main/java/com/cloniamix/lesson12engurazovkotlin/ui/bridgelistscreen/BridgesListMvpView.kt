package com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen

import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.MvpView

interface BridgesListMvpView : MvpView {
    fun showBridges(bridges: List<Bridge>)
    //    fun showBridgeDetails(bridge: Bridge)
//    fun showErrorState()
//    fun showProgress()
    fun showState(stateFlag: Int)
}