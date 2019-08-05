package com.cloniamix.lesson12engurazovkotlin.mvp.contract.basepresenter

import com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview.MvpView

interface Presenter<V : MvpView> {
    fun attachView(mvpView: V)
    fun detachView()
}