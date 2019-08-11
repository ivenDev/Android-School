package com.cloniamix.lesson12engurazovkotlin.ui.bridgesinmapscreen

import android.util.Log
import com.cloniamix.lesson12engurazovkotlin.MyApplication.Companion.APP_TAG
import com.cloniamix.lesson12engurazovkotlin.data.BridgesData
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.data.model.BridgeHelper
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
    //private var bridges: List<Bridge> = BridgesData.getInstance()!!.getBridgesList()

    fun onViewCreated() {
        checkViewAttached()
        getBridgesList()

        //fixme: если данные уже есть, то они приходят быстрее, чем отрисовывается карта
        /*if (bridges.isEmpty()) {
            getBridgesList()
        } else {
            updateUi(bridges)
        }*/
    }

    private fun getBridgesList() {

        getMvpView()?.showState(FLAG_PROGRESS)

        disposable = bridgesRepository.getBridges()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ bridges ->
                BridgesData.getInstance().setBridgesList(bridges)
                updateUi(bridges)
            },
                { t -> error(t) }
            )


    }

    private fun updateUi(bridges: List<Bridge>) {

        if (isViewAttached()) {
            getMvpView()?.showState(FLAG_DATA)
            getMvpView()?.addMarkers(bridges)
        }
    }

    /*private fun createMarkers(bridges: List<Bridge>) {
        var count = 0
        for (bridge in bridges) {
            val markerOptions = MarkerOptions()
                .position(LatLng(bridge.lat, bridge.lng))
                .title(bridge.name)
                .icon(BitmapDescriptorFactory.fromResource(getStatusIconResId(bridge)))
            val marker: Marker? = map?.addMarker(markerOptions)
            marker?.tag = count
            count++
        }
    }*/

    private fun error(t: Throwable?) {

        Log.d(APP_TAG, t.toString())

        if (isViewAttached()) {
            getMvpView()?.showState(FLAG_ERROR)
        }
    }

    fun getBridges(): List<Bridge> {
        return BridgesData.getInstance().getBridgesList()
    }

    fun getStatusIconResId(bridge: Bridge): Int {
        return BridgeHelper.getStatusIconPngResId(bridge)
    }

    fun getDivorceTime(bridge: Bridge): String {
        return BridgeHelper.getStringDivorceTime(bridge.divorces)
    }

    fun onRefresh() {
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
}