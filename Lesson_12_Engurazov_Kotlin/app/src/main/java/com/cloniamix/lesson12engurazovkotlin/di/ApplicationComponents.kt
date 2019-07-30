package com.cloniamix.lesson12engurazovkotlin.di

import android.content.Context
import com.cloniamix.lesson12engurazovkotlin.mvp.model.remote.BridgeApi
import com.cloniamix.lesson12engurazovkotlin.mvp.model.remote.BridgeApiService
import com.cloniamix.lesson12engurazovkotlin.provider.BridgesRepository

class ApplicationComponents private constructor(context: Context){

    private var context: Context? = null
    private var apiService: BridgeApi
    private var bridgesRepository: BridgesRepository

    init {
        this.context = context
        apiService = BridgeApiService.getClient
        bridgesRepository = BridgesRepository(apiService)
    }

    companion object {

        private var INSTANCE: ApplicationComponents? = null //fixme: ругается на передачу контекста

        fun getInstance (context: Context): ApplicationComponents?{
            if (INSTANCE == null){
                synchronized(ApplicationComponents::class.java){
                    INSTANCE = ApplicationComponents(context)

                }
            }

            return INSTANCE
        }
    }



    fun provideApiService(): BridgeApi{
        return apiService
    }

    fun provideContext(): Context?{
        return context
    }

//    fun provideBridgesAdapter(){
//        return BridgesAdapter()
//    }

//    fun providePridgesListPresenter(): BridgesListPresenter {
//        return BridgesListPresenter(provideBridgesProvider())
//    }
//
    fun provideBridgesProvider(): BridgesRepository {
        return bridgesRepository
    }
}