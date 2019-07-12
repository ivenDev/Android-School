package com.cloniamix.lesson_8_engurazov_kotlin.utils

import android.view.View

class Utils {

    companion object {
        fun setViewVisible(view: View?, show: Boolean) {
            if (view == null) return
            if (show) view.visibility = View.VISIBLE
            else view.visibility = View.GONE
        }
    }


}