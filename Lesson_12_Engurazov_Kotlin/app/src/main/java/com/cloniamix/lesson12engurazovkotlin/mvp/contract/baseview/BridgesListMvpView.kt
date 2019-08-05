package com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview

import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.Bridge
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview.FragmentMvpView

interface BridgesListMvpView : FragmentMvpView {
    fun showBridges(bridges: List<Bridge>)
}