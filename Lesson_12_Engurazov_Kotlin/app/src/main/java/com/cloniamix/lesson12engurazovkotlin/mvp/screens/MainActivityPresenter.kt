package com.cloniamix.lesson12engurazovkotlin.mvp.screens

import android.util.Log
import com.cloniamix.lesson12engurazovkotlin.app.MyApplication.Companion.APP_TAG
import com.cloniamix.lesson12engurazovkotlin.mvp.model.BridgesData
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basepresenter.BasePresenter
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview.MainActivityMvpView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityPresenter :
    BasePresenter<MainActivityMvpView>() {

    private var disposable: Disposable? = null

    fun onCreate() {
        checkViewAttached()
        getBridgesList()
        if (isViewAttached()) {
            getMvpView()?.showBridgesListFragment()
        }
    }

    fun onBridgeItemClicked(bridgeId: Int) {
        getMvpView()?.showBridgeDetailsFragment(bridgeId)
    }

    fun onMenuMapItemClicked() {
        getMvpView()?.showBridgesInMapFragment()
    }

    private fun getBridgesList() {
        disposable = bridgesRepository.getBridges()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { bridges -> BridgesData.getInstance().setBridgesList(bridges) },
                {throwable -> Log.d(APP_TAG, "Error: $throwable") }
                )
    }

    override fun doUnsubscribe() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}