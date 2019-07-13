package com.cloniamix.lesson_9_engurazov_kotlin.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.cloniamix.lesson_9_engurazov_kotlin.ServiceCallbacks
import com.cloniamix.lesson_9_engurazov_kotlin.network.WeatherApiClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherService : Service() {

    inner class MyBinder : Binder(){
        fun getService(): WeatherService
        {
            return this@WeatherService
        }
    }

    private val binder = MyBinder()
    private var serviceCallbacks: ServiceCallbacks? = null
    private var disposable: Disposable? = null
    private var observable: Disposable? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyTAG", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("MyTAG", "onBind")

        getWeather()

        observable = Observable.interval(10, java.util.concurrent.TimeUnit.SECONDS)
            .subscribe{ getWeather() }
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("MyTAG", "onUnbind")
        disposable?.dispose()
        observable?.dispose()
        return super.onUnbind(intent)

    }

    fun setCallbacks(callbacks: ServiceCallbacks?){
        serviceCallbacks = callbacks
    }


    private fun getWeather(){
        Log.d("MyTAG", "getWeather called")
        disposable = WeatherApiClient.getClient.getWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { t1 -> serviceCallbacks?.setWeather(t1)},
                { t -> serviceCallbacks?.error(t.message) } )

    }

}