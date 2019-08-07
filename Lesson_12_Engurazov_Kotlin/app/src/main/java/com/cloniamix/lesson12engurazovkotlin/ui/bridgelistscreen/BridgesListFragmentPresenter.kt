package com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen

import android.util.Log
import com.cloniamix.lesson12engurazovkotlin.MyApplication.Companion.APP_TAG
import com.cloniamix.lesson12engurazovkotlin.data.BridgesData
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BridgesListFragmentPresenter : BasePresenter<BridgesListMvpView>() {

    private var disposable: Disposable? = null
    private var bridges: List<Bridge> = BridgesData.getInstance().getBridgesList()

    fun onViewCreated() {
        checkViewAttached()
        getMvpView()?.showState(FLAG_PROGRESS)

        if (bridges.isEmpty()) {
            getBridgesList()
        } else {
            updateUi(bridges)
        }
    }

    fun onRefresh() {
        getMvpView()?.showState(FLAG_PROGRESS)
        getBridgesList()
    }

    fun onSwipeRefresh() {
        getBridgesList()
    }

    private fun getBridgesList() {
        disposable = bridgesRepository.getBridges()
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bridges -> updateUi(bridges) },
                    { t -> error(t) }
                )
    }

    private fun updateUi(bridges: List<Bridge>) {
        BridgesData.getInstance().setBridgesList(bridges)

        if (isViewAttached()) {
            getMvpView()?.showState(FLAG_DATA)
            getMvpView()?.showBridges(bridges)
        }
    }

    private fun error(t: Throwable) {

        Log.d(APP_TAG, t.toString())

        if (isViewAttached()) {
            getMvpView()?.showState(FLAG_ERROR)
        }
    }

    override fun doUnsubscribe() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}