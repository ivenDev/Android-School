package com.cloniamix.lesson12engurazovkotlin

import android.app.Application
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents

class MyApplication : Application() {

    companion object {
        const val APP_TAG = "lesson12"
    }

    private lateinit var applicationComponents: ApplicationComponents

    override fun onCreate() {
        super.onCreate()
        applicationComponents = ApplicationComponents.getInstance(/*this*/)!!
    }

    fun getApplicationComponents(): ApplicationComponents{
        return applicationComponents
    }
}