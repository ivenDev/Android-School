package com.cloniamix.lesson12engurazovkotlin.ui.bridgesinmapscreen

import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.MvpView

interface BridgesInMapMvpView : MvpView {
    fun showState(stateFlag: Int)
    fun addMarkers(bridges: List<Bridge>)
}