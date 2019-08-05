package com.cloniamix.lesson12engurazovkotlin.ui.bridgesinmapscreen

import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.FragmentMvpView

interface BridgesInMapMvpView : FragmentMvpView {
    fun addMarkers(bridges: List<Bridge>)
}