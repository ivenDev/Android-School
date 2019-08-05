package com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview

import androidx.appcompat.app.AppCompatActivity
import com.cloniamix.lesson12engurazovkotlin.app.MyApplication
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents

abstract class BaseActivity : AppCompatActivity() {

    fun getApplicationComponents(): ApplicationComponents {
        return (application as MyApplication).getApplicationComponents()
    }

}