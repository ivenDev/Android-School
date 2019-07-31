package com.cloniamix.lesson12engurazovkotlin.ui.base

interface Presenter<V : MvpView> {
    fun attachView(mvpView: V)
    fun detachView()
}