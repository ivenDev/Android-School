package com.cloniamix.lesson_7_engurazov_kotlin

import android.util.Log
import android.view.View

class Utils {

    companion object {
        private const val APP_TAG: String = "bridgeAppTag"

        fun log(message: String) {
            Log.d(APP_TAG, message)
        }

        fun setViewVisible(view: View?, show: Boolean) {
            if (view == null) return
            if (show) view.visibility = View.VISIBLE
            else view.visibility = View.GONE
        }
    }
}