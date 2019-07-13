package com.cloniamix.lesson_9_engurazov_kotlin.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.content.Context
import com.cloniamix.lesson_9_engurazov_kotlin.MainActivity
import com.cloniamix.lesson_9_engurazov_kotlin.network.WeatherApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class DownloadService : Service() {

    private var disposable: Disposable? = null

    companion object {
        fun createStartIntent(context: Context): Intent{
            return Intent(context, DownloadService::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val channelId = "com.cloniamix.lesson_9_engurazov_kotlin.services"
        /*val notificationManager = initNotificationManager(this, channelId)*/
        val notificationBuilder = createNotificationBuilder(this, channelId)
        startForeground(1, notificationBuilder.build())
    }

    /*private fun initNotificationManager(context: Context, channelId: String): NotificationManager {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Заголовок канала",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
            return notificationManager
        } else {
            return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
    }*/

    private fun createNotificationBuilder(context: Context, channelId: String): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, channelId)
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
        builder.setContentTitle("Заголовок")
        builder.priority = NotificationCompat.PRIORITY_LOW
        builder.setContentText("Текст")
        builder.setCategory(NotificationCompat.CATEGORY_SERVICE)
        builder.setOngoing(true)
        return builder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        some()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun some() {

        //todo: реализовать загрузку архива и его распаковку
        disposable = WeatherApiClient.getClient.getWeather()
            .subscribeOn(Schedulers.io())
            .delay(10, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{t -> val intent = Intent(MainActivity.MY_ACTION)
        intent.putExtra("TEST", "Привет из сервиса ${t.name}")
        sendBroadcast(intent)
        stopSelf()}

    }

}
