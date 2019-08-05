package com.cloniamix.lesson12engurazovkotlin.ui.bridgedetailsscreen

import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.FragmentMvpView

interface BridgeDetailsMvpView : FragmentMvpView{
    fun showBridgeDetails(bridge: Bridge)
}