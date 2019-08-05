package com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen

import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.FragmentMvpView

interface BridgesListMvpView : FragmentMvpView {
    fun showBridges(bridges: List<Bridge>)
}