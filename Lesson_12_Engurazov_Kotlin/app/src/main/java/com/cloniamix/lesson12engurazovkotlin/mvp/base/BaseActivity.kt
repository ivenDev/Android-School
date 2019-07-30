package com.cloniamix.lesson12engurazovkotlin.mvp.base

import androidx.appcompat.app.AppCompatActivity
import com.cloniamix.lesson12engurazovkotlin.MyApplication
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents

abstract class BaseActivity : AppCompatActivity() {

//    protected fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    fun getApplicationComponents(): ApplicationComponents{
        return (application as MyApplication).getApplicationComponents()
    }

}