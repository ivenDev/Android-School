package com.cloniamix.lesson12engurazovkotlin.ui.base

import androidx.annotation.NonNull
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents


abstract class BasePresenter<T : MvpView> : Presenter<T> {

    companion object {
        const val FLAG_PROGRESS = 0
        const val FLAG_ERROR = 1
        const val FLAG_DATA = 2
    }

    private var mvpView: T? = null

    @NonNull
    protected val bridgesRepository = ApplicationComponents.getInstance().provideBridgesRepository()

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
        doUnsubscribe()
    }

    protected fun isViewAttached(): Boolean {
        return mvpView != null
    }

    protected fun getMvpView(): T? {
        return mvpView
    }

    protected fun checkViewAttached() {
        if (!isViewAttached()) throw MvpViewNotAttachedException()
    }

    protected abstract fun doUnsubscribe()


    class MvpViewNotAttachedException :
        RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")
}