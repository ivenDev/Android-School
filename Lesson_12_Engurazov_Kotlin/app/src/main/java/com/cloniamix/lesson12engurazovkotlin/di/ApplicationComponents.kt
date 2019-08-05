package com.cloniamix.lesson12engurazovkotlin.di

import com.cloniamix.lesson12engurazovkotlin.data.remote.BridgeApi
import com.cloniamix.lesson12engurazovkotlin.data.remote.BridgeApiService
import com.cloniamix.lesson12engurazovkotlin.provider.BridgesRepository
import com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen.BridgesAdapter
import com.cloniamix.lesson12engurazovkotlin.ui.MainActivityPresenter
import com.cloniamix.lesson12engurazovkotlin.ui.bridgedetailsscreen.BridgeDetailsFragmentPresenter
import com.cloniamix.lesson12engurazovkotlin.ui.bridgelistscreen.BridgesListFragmentPresenter
import com.cloniamix.lesson12engurazovkotlin.ui.bridgesinmapscreen.BridgesInMapFragmentPresenter

class ApplicationComponents private constructor() {

    private var apiService: BridgeApi = BridgeApiService.getClient
    private var bridgesRepository: BridgesRepository = BridgesRepository(provideApiService())


    companion object {

        private var INSTANCE: ApplicationComponents? = null

        fun getInstance(): ApplicationComponents? {
            if (INSTANCE == null) {
                synchronized(ApplicationComponents::class.java) {
                    INSTANCE = ApplicationComponents()
                }
            }
            return INSTANCE
        }
    }

    fun provideApiService(): BridgeApi {
        return apiService
    }


    fun provideBridgesAdapter(): BridgesAdapter {
        return BridgesAdapter()
    }

    fun provideMainActivityPresenter(): MainActivityPresenter {
        return MainActivityPresenter()
    }

    fun provideBridgesListPresenter(): BridgesListFragmentPresenter {
        return BridgesListFragmentPresenter()
    }

    fun provideBridgeDetailsPresenter(): BridgeDetailsFragmentPresenter {
        return BridgeDetailsFragmentPresenter()
    }

    fun provideBridgesInMapFragmentPresenter(): BridgesInMapFragmentPresenter {
        return BridgesInMapFragmentPresenter()
    }

    fun provideBridgesRepository(): BridgesRepository {
        return bridgesRepository
    }
}