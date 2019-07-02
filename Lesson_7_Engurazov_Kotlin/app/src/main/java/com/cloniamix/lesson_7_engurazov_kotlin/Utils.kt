package com.cloniamix.lesson_7_engurazov_kotlin

import android.util.Log
import android.view.View
import com.cloniamix.lesson_7_engurazov_kotlin.pojo.Divorce
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        private const val APP_TAG: String = "bridgeAppTag"
        const val BASE_URL = "https://gdemost.handh.ru"

        const val STATUS_NORMAL: Int = 1 // мост сведен
        const val STATUS_SOON: Int = 2 //мост скоро разведется
        const val STATUS_LATE: Int = 3 // мост разведен


        fun log(message: String) {
            Log.d(APP_TAG, message)
        }

        fun setViewVisible(view: View?, show: Boolean) {
            if (view == null) return
            if (show) view.visibility = View.VISIBLE
            else view.visibility = View.GONE
        }

        fun getStringDivorceTime(divorceTimesList: List<Divorce>): String{
            val formatter = SimpleDateFormat("h:mm")
            var divorceTime = ""
            for (divorce in divorceTimesList){
                divorceTime = divorceTime +  formatter.format(divorce.start) + " - " + formatter.format(divorce.end) + "  "
            }
            return divorceTime
        }

        fun getBridgeStatus(divorceTimesList: List<Divorce>): Int{

            var bridgeStatus = STATUS_NORMAL

            val currentTime = Calendar.getInstance()

            for (divorce in divorceTimesList){

                val startTime: Calendar = Calendar.getInstance()
                startTime.timeInMillis = divorce.start.time
                startTime.set(currentTime[Calendar.YEAR], currentTime[Calendar.MONTH], currentTime[Calendar.DATE])

                val endTime: Calendar = Calendar.getInstance()
                endTime.timeInMillis = divorce.end.time
                endTime.set(currentTime[Calendar.YEAR], currentTime[Calendar.MONTH], currentTime[Calendar.DATE])

                if (startTime.timeInMillis - currentTime.timeInMillis in 0..3600000)
                    bridgeStatus = STATUS_SOON

                if (currentTime.after(startTime) && currentTime.before(endTime))
                    bridgeStatus = STATUS_LATE
            }

            return bridgeStatus
        }
    }
}