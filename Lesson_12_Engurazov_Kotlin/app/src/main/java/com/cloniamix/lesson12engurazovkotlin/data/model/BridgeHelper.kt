package com.cloniamix.lesson12engurazovkotlin.data.model

import android.text.format.DateUtils
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.data.remote.BridgeApiService
import java.text.SimpleDateFormat
import java.util.*

class BridgeHelper {

    companion object {

        private const val STATUS_NORMAL: Int = 1 // мост сведен
        private const val STATUS_SOON: Int = 2   // мост скоро разведется
        private const val STATUS_LATE: Int = 3   // мост разведен


        /**
         * Преобразует время развода моста в формат "чч.мм - чч.мм". Если временных диапазонов несколько, то
         * преобразует их все и возвращает в одной String переменной
         *
         * @param divorceTimesList список временных диапазонов, когда мост будет разведен*/
        fun getStringDivorceTime(divorceTimesList: List<Divorce>): String {
            val formatter = SimpleDateFormat("h:mm", Locale.ROOT)
            var divorceTime = ""
            for (divorce in divorceTimesList) {
                divorceTime =
                    divorceTime + formatter.format(divorce.start) + " - " + formatter.format(divorce.end) + "  "
            }
            return divorceTime
        }

        /**
         * Определяет статус моста (сведен, разведен, до развода осталось меньше часа) и возвращает id ресурса
         * нужной иконки(xml)
         *
         * @param bridge экземпляр класса Bridge, для которого нужно получить статус-иконку
         *
         * @see Bridge*/
        fun getStatusIconResId(bridge: Bridge): Int {
            return when (getBridgeStatus(bridge.divorces)) {
                STATUS_SOON -> R.drawable.ic_bridge_soon
                STATUS_LATE -> R.drawable.ic_bridge_late
                else -> R.drawable.ic_bridge_normal
            }
        }

        /**
         * Определяет статус моста (сведен, разведен, до развода осталось меньше часа) и возвращает id ресурса
         * нужной иконки(png)
         *
         * @param bridge экземпляр класса Bridge, для которого нужно получить статус-иконку
         *
         * @see Bridge*/
        fun getStatusIconPngResId(bridge: Bridge): Int {
            return when (getBridgeStatus(bridge.divorces)) {
                STATUS_SOON -> R.drawable.ic_brige_soon_png
                STATUS_LATE -> R.drawable.ic_brige_late_png
                else -> R.drawable.ic_brige_normal_png
            }
        }

        /**
         * Определяет статус моста (сведен, разведен) и возвращает url
         * соответствующей фотографии моста
         *
         * @param bridge экземпляр класса Bridge, для которого нужно получить фотографию
         *
         * @see Bridge*/
        fun getBridgePhotoUrl(bridge: Bridge): String {
            return when (getBridgeStatus(bridge.divorces) == STATUS_LATE) {
                true -> "${BridgeApiService.BASE_URL}/${bridge.photoClose}"
                false -> "${BridgeApiService.BASE_URL}/${bridge.photoOpen}"
            }
        }

        /**
         * Возвращает статус моста. STATUS_NORMAL - мост сведен, STATUS_SOON - до развода моста осталось меньше часа
         * STATUS_LATE - мост разведен
         *
         * @param divorceTimesList список временных диапазонов, когда мост будет разведен*/
        private fun getBridgeStatus(divorceTimesList: List<Divorce>): Int {

            var bridgeStatus = STATUS_NORMAL

            val currentTime = Calendar.getInstance()

            for (divorce in divorceTimesList) {

                val startTime: Calendar = Calendar.getInstance()
                startTime.timeInMillis = divorce.start.time
                startTime.set(currentTime[Calendar.YEAR], currentTime[Calendar.MONTH], currentTime[Calendar.DATE])

                val endTime: Calendar = Calendar.getInstance()
                endTime.timeInMillis = divorce.end.time
                endTime.set(currentTime[Calendar.YEAR], currentTime[Calendar.MONTH], currentTime[Calendar.DATE])

                if (bridgeStatus != STATUS_LATE && startTime.timeInMillis - currentTime.timeInMillis in 0..DateUtils.HOUR_IN_MILLIS)
                    bridgeStatus = STATUS_SOON

                if (currentTime.after(startTime) && currentTime.before(endTime))
                    bridgeStatus = STATUS_LATE
            }

            return bridgeStatus
        }
    }

}