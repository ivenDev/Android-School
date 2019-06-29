package com.cloniamix.lesson_7_engurazov_kotlin.screens.screenbridgedetails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cloniamix.lesson_7_engurazov_kotlin.R
import com.cloniamix.lesson_7_engurazov_kotlin.pojo.Bridge
import kotlinx.android.synthetic.main.activity_bridge_details.*

class BridgeDetailsActivity : AppCompatActivity() {



    companion object {
        private const val DATA_KEY: String = "MyBridgeObject"

        fun createStartIntent(context: Context, bridge: Bridge): Intent{

            val intent = Intent(context, BridgeDetailsActivity::class.java)
            intent.putExtra(DATA_KEY, bridge)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge_details)

//        val bridge: String? = intent.getStringExtra(DATA_KEY)
//
//        textView.text = bridge

        val bridge: Bridge = intent.getParcelableExtra(DATA_KEY)

        textView.text = bridge.divorces[0].start

    }
}
