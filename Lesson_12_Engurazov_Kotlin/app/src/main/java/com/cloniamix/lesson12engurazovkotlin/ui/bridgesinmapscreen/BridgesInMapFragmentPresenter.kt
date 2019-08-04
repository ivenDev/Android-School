package com.cloniamix.lesson12engurazovkotlin.ui.bridgesinmapscreen

import android.util.Log
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.data.BridgesData
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.ui.base.BasePresenter
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BridgesInMapFragmentPresenter : BasePresenter<BridgesInMapMvpView>() {

    companion object {
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }

    private var disposable: Disposable? = null


    fun onViewCreated() {
        checkViewAttached()
        getMvpView()?.showState(FLAG_PROGRESS)
        getBridgesList()
    }

    /*private fun getBridgesList() {
        disposable = bridgesRepository?.getBridges()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ bridges -> updateUi(bridges) },
                { t -> error(t) }
            )
    }*/

    private fun updateUi(bridges: List<Bridge>) {
        BridgesData.getInstance()?.setBridgesList(bridges)

        if (isViewAttached()) {
            getMvpView()?.showState(FLAG_DATA)
            getMvpView()?.addMarkers(bridges)
        }
    }

    private fun error(t: Throwable?) {

        Log.d("MyTag", t.toString())

        if (isViewAttached()) {
            getMvpView()?.showState(FLAG_ERROR)
        }
    }

    fun getBridges(): List<Bridge> {
        return BridgesData.getInstance()!!.getBridgesList()
    }

    fun getStatusIconResId(bridge: Bridge): Int {
        return when (getBridgeStatus(bridge.divorces)) {
            STATUS_SOON -> R.drawable.ic_brige_soon_png
            STATUS_LATE -> R.drawable.ic_brige_late_png
            else -> R.drawable.ic_brige_normal_png
        }
    }

    fun getDivorceTime(bridge: Bridge): String {
        return getStringDivorceTime(bridge.divorces)
    }

    fun onRefresh() {
        getMvpView()?.showState(FLAG_PROGRESS)
        getBridgesList()
    }

    fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest()

        locationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        return locationRequest
    }

    fun buildLocationSettingsRequest(): LocationSettingsRequest {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(createLocationRequest())

        return builder.build()
    }


    override fun doUnsubscribe() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }

    override fun doInOk(bridges: List<Bridge>) {
        updateUi(bridges)
    }

    override fun doError(t: Throwable) {
        error(t)
    }
}