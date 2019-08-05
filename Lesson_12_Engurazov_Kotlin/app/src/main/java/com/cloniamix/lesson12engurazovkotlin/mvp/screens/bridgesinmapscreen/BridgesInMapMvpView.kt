package com.cloniamix.lesson12engurazovkotlin.mvp.screens.bridgesinmapscreen

import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.Bridge
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview.FragmentMvpView

interface BridgesInMapMvpView : FragmentMvpView {
    fun addMarkers(bridges: List<Bridge>)
}