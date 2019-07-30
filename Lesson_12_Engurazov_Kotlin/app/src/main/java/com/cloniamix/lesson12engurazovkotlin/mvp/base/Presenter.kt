package com.cloniamix.lesson12engurazovkotlin.mvp.base

interface Presenter<V : MvpView> {
    fun attachView(mvpView: V)
    fun detachView()
}