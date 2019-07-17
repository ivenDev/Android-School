package com.cloniamix.lesson10engurazovkotlin.screens.screenbridgedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cloniamix.lesson10engurazovkotlin.R
import com.cloniamix.lesson10engurazovkotlin.pojo.Bridge
import com.cloniamix.lesson10engurazovkotlin.utils.Utils
import com.cloniamix.lesson10engurazovkotlin.utils.Utils.Companion.BASE_URL
import com.cloniamix.lesson10engurazovkotlin.utils.Utils.Companion.STATUS_LATE
import com.cloniamix.lesson10engurazovkotlin.utils.Utils.Companion.STATUS_NORMAL
import com.cloniamix.lesson10engurazovkotlin.utils.Utils.Companion.STATUS_SOON
import kotlinx.android.synthetic.main.activity_bridge_details.*
import kotlinx.android.synthetic.main.view_bridge_item.*

class BridgeDetailsActivity : AppCompatActivity() {

    companion object {
        private const val DATA_KEY: String = "MyBridgeObject"

        fun createStartIntent(context: Context, bridge: Bridge): Intent {

            val intent = Intent(context, BridgeDetailsActivity::class.java)
            intent.putExtra(DATA_KEY, bridge)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge_details)

        toolbarBridgeDetail.setNavigationOnClickListener { onBackPressed() }

        val bridge: Bridge = intent.getParcelableExtra(DATA_KEY)
        updateUi(bridge)
    }

    private fun updateUi(bridge: Bridge) {
        textViewBridgeName.text = bridge.name
        textViewTime.text = Utils.getStringDivorceTime(bridge.divorces)
        setStatusIconResId(Utils.getBridgeStatus(bridge.divorces))

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textViewDescription.text = Html.fromHtml(bridge.description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            textViewDescription.text = Html.fromHtml(bridge.description)
        }
        setBridgePhoto(bridge)
    }

    private fun setBridgePhoto(bridge: Bridge) {
        var bridgePhoto: String
        if (Utils.getBridgeStatus(bridge.divorces) == STATUS_LATE) bridgePhoto = bridge.photoClose
        else bridgePhoto = bridge.photoOpen

        Glide.with(this)
            .load("$BASE_URL/$bridgePhoto")
            .centerCrop()
            .placeholder(R.drawable.ic_no_content)
            .into(imageViewBridgePhoto)

    }

    private fun setStatusIconResId(status: Int) {
        when (status) {
            STATUS_SOON -> {
                imageViewStatusIcon.setImageResource(R.drawable.ic_bridge_soon)
            }
            STATUS_LATE -> {
                imageViewStatusIcon.setImageResource(R.drawable.ic_bridge_late)
            }
            STATUS_NORMAL -> {
                imageViewStatusIcon.setImageResource(R.drawable.ic_bridge_normal)
            }
        }
    }
}