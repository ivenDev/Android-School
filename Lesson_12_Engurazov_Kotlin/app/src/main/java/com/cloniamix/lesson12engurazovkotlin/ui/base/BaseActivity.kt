package com.cloniamix.lesson12engurazovkotlin.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.cloniamix.lesson12engurazovkotlin.MyApplication
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents

abstract class BaseActivity : AppCompatActivity() {

    fun getApplicationComponents(): ApplicationComponents {
        return (application as MyApplication).getApplicationComponents()
    }

}