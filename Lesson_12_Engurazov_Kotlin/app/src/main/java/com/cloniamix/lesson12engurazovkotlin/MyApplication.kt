package com.cloniamix.lesson12engurazovkotlin

import android.app.Application
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents

class MyApplication : Application() {

    private lateinit var applicationComponents: ApplicationComponents

    override fun onCreate() {
        super.onCreate()
        applicationComponents = ApplicationComponents.getInstance(/*this*/)!!
    }

    fun getApplicationComponents(): ApplicationComponents{
        return applicationComponents
    }
}