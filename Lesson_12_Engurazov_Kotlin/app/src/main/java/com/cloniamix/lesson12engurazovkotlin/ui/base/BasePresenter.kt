package com.cloniamix.lesson12engurazovkotlin.ui.base

import androidx.annotation.NonNull
import com.cloniamix.lesson12engurazovkotlin.data.BridgesData
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


abstract class BasePresenter<T : MvpView> : Presenter<T> {

    private var mvpView: T? = null
    @NonNull
    protected val bridgesRepository = ApplicationComponents.getInstance()?.provideBridgesRepository()
    //private var disposable: Disposable? = null


    override fun attachView(mvpView: T) {
         this.mvpView = mvpView
     }

     override fun detachView() {
         mvpView = null
         doUnsubscribe()
     }

    protected abstract fun doUnsubscribe()

    fun isViewAttached(): Boolean{
        return mvpView != null
    }

    protected fun getMvpView(): T?{
        return mvpView
    }

    protected fun checkViewAttached() {
        if (!isViewAttached()) throw MvpViewNotAttachedException()
    }

//    fun getBridgesList() {
//        disposable = bridgesRepository.getBridges()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe{ bridges -> BridgesData.getInstance()?.setBridgesList(bridges)}
//    }

    class MvpViewNotAttachedException :
        RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")
 }