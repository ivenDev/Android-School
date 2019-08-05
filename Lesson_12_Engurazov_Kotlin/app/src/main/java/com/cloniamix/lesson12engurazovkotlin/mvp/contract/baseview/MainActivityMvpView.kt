package com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview

import com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview.MvpView

interface MainActivityMvpView : MvpView {

    fun showBridgesListFragment()
    fun showBridgeDetailsFragment(bridgeId: Int)
    fun showBridgesInMapFragment()
}