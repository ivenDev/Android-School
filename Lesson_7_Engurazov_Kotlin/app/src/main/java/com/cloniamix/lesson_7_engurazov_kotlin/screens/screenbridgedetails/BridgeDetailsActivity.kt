package com.cloniamix.lesson_7_engurazov_kotlin.screens.screenbridgedetails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.cloniamix.lesson_7_engurazov_kotlin.R
import com.cloniamix.lesson_7_engurazov_kotlin.Utils
import com.cloniamix.lesson_7_engurazov_kotlin.Utils.Companion.BASE_URL
import com.cloniamix.lesson_7_engurazov_kotlin.Utils.Companion.STATUS_LATE
import com.cloniamix.lesson_7_engurazov_kotlin.Utils.Companion.STATUS_NORMAL
import com.cloniamix.lesson_7_engurazov_kotlin.Utils.Companion.STATUS_SOON
import com.cloniamix.lesson_7_engurazov_kotlin.pojo.Bridge
import kotlinx.android.synthetic.main.activity_bridge_details.*
import kotlinx.android.synthetic.main.view_bridge_item.*

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

        val bridge: Bridge = intent.getParcelableExtra(DATA_KEY)

        textViewBridgeName.text = bridge.name
        textViewTime.text = Utils.getStringDivorceTime(bridge.divorces)
        setStatusIconResId(Utils.getBridgeStatus(bridge.divorces))

        if(android.os.Build.VERSION.SDK_INT>= android.os.Build.VERSION_CODES.N) {
            textViewDescription.text = Html.fromHtml(bridge.description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            textViewDescription.text = Html.fromHtml(bridge.description)
        }

        Glide.with(this)
                .load("$BASE_URL/${bridge.photoClose}")
                .centerCrop()
                .placeholder(R.drawable.ic_no_content)
                .into(imageViewBridgePhoto)

    }

    private fun setStatusIconResId(status: Int) {
        when(status){
            STATUS_SOON -> {imageViewStatusIcon.setImageResource(R.drawable.ic_bridge_soon)}
            STATUS_LATE -> {imageViewStatusIcon.setImageResource(R.drawable.ic_bridge_late)}
            STATUS_NORMAL -> {imageViewStatusIcon.setImageResource(R.drawable.ic_bridge_normal)}
        }
    }
}
