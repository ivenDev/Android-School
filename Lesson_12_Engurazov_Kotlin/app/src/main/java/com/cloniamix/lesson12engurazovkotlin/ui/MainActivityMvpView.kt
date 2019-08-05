package com.cloniamix.lesson12engurazovkotlin.ui

import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.MvpView

interface MainActivityMvpView : MvpView {

    fun showBridgesListFragment()
    fun showBridgeDetailsFragment(bridgeId: Int)
    fun showBridgesInMapFragment()
}