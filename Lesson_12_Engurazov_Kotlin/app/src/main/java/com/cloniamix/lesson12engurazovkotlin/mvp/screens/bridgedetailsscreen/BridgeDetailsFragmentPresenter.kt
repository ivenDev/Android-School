package com.cloniamix.lesson12engurazovkotlin.mvp.screens.bridgedetailsscreen

import android.util.Log
import com.cloniamix.lesson12engurazovkotlin.app.MyApplication.Companion.APP_TAG
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.Bridge
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.remote.BridgeApiService.BASE_URL
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basepresenter.BasePresenter
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview.BridgeDetailsMvpView
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
        disposable = bridgesRepository?.getBridgeInfoById(bridgeId)?.let {
            it
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bridge -> updateUi(bridge) },
                    { t: Throwable? -> onError(t) }
                )
        }

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
        return when (getBridgeStatus(bridge.divorces)) {
            STATUS_SOON -> R.drawable.ic_bridge_soon
            STATUS_LATE -> R.drawable.ic_bridge_late
            else -> R.drawable.ic_bridge_normal
        }
    }


    fun getDivorceTime(bridge: Bridge): String {
        return getStringDivorceTime(bridge.divorces)
    }

    fun getBridgePhotoUrl(bridge: Bridge): String {
        return when (getBridgeStatus(bridge.divorces) == STATUS_LATE) {
            true -> "$BASE_URL/${bridge.photoClose}"
            false -> "$BASE_URL/${bridge.photoOpen}"
        }
    }

    override fun doUnsubscribe() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }


}