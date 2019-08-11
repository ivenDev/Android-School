package com.cloniamix.lesson12engurazovkotlin.ui.bridgedetailsscreen

import android.util.Log
import com.cloniamix.lesson12engurazovkotlin.MyApplication.Companion.APP_TAG
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.data.model.BridgeHelper
import com.cloniamix.lesson12engurazovkotlin.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BridgeDetailsFragmentPresenter : BasePresenter<BridgeDetailsMvpView>() {

    private var disposable: Disposable? = null


    fun onViewCreated(bridgeId: Int) {
        checkViewAttached()
        getMvpView()?.showState(FLAG_PROGRESS)
        getBridgeDetailsById(bridgeId)
    }

    fun onRefresh(bridgeId: Int) {
        getMvpView()?.showState(FLAG_PROGRESS)
        getBridgeDetailsById(bridgeId)
    }

    private fun getBridgeDetailsById(bridgeId: Int) {
        disposable = bridgesRepository.getBridgeInfoById(bridgeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bridge -> updateUi(bridge) },
                    { t: Throwable? -> onError(t) }
                )

    }

    private fun onError(t: Throwable?) {

        Log.d(APP_TAG, t.toString())

        if (isViewAttached()) {
            getMvpView()?.showState(FLAG_ERROR)
        }
    }

    private fun updateUi(bridge: Bridge) {
        if (isViewAttached()) {
            getMvpView()?.showState(FLAG_DATA)
            getMvpView()?.showBridgeDetails(bridge)
        }
    }

    fun getStatusIconResId(bridge: Bridge): Int {
        return BridgeHelper.getStatusIconResId(bridge)
    }


    fun getDivorceTime(bridge: Bridge): String {
        return BridgeHelper.getStringDivorceTime(bridge.divorces)
    }

    fun getBridgePhotoUrl(bridge: Bridge): String {
        return BridgeHelper.getBridgePhotoUrl(bridge)
    }

    override fun doUnsubscribe() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }


}