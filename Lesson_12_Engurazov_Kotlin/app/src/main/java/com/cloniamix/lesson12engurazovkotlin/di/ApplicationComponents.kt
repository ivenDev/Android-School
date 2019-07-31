package com.cloniamix.lesson12engurazovkotlin.di

import android.content.Context
import com.cloniamix.lesson12engurazovkotlin.data.remote.BridgeApi
import com.cloniamix.lesson12engurazovkotlin.data.remote.BridgeApiService
import com.cloniamix.lesson12engurazovkotlin.provider.BridgesRepository
import com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen.BridgesAdapter
import com.cloniamix.lesson12engurazovkotlin.ui.MainActivityPresenter
import com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen.BridgesListFragmentPresenter

class ApplicationComponents private constructor(/*context: Context*/){

    //private var context: Context? = null
    private var apiService: BridgeApi= BridgeApiService.getClient
    private var bridgesRepository: BridgesRepository = BridgesRepository(apiService)

    /*init {
        //this.context = context
        //apiService = BridgeApiService.getClient
        //bridgesRepository = BridgesRepository(apiService)
    }*/

    companion object {

        private var INSTANCE: ApplicationComponents? = null //fixme: ругается на передачу контекста

        fun getInstance (/*context: Context*/): ApplicationComponents?{
            if (INSTANCE == null){
                synchronized(ApplicationComponents::class.java){
                    INSTANCE = ApplicationComponents(/*context*/)

                }
            }

            return INSTANCE
        }
    }



    fun provideApiService(): BridgeApi{
        return apiService
    }

//    fun provideContext(): Context?{
//        return context
//    }

    fun provideBridgesAdapter(): BridgesAdapter {
        return BridgesAdapter()
    }

    fun provideMainActivityPresenter(): MainActivityPresenter {
        return MainActivityPresenter(/*provideBridgesRepository()*/)
    }
    fun provideBridgeListPresenter(): BridgesListFragmentPresenter {
        return BridgesListFragmentPresenter(/*provideBridgesRepository()*/)
    }

    fun provideBridgesRepository(): BridgesRepository {
        return bridgesRepository
    }
}