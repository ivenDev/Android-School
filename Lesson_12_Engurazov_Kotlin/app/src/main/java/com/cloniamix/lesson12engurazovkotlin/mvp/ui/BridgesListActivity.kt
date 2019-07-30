package com.cloniamix.lesson12engurazovkotlin.mvp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.mvp.base.BaseActivity

class BridgesListActivity : BaseActivity(), BridgesListMvpView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
