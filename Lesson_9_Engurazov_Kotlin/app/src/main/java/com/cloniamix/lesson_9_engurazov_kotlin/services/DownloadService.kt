package com.cloniamix.lesson_9_engurazov_kotlin.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class DownloadService : Service() {

    companion object {
        fun createStartIntent(context: Context): Intent{
            return Intent(context, DownloadService::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val channelId = "com.cloniamix.lesson_9_engurazov_kotlin.services"
        val notificationBuilder = createNotificationBuilder(this, channelId)
        startForeground(1, notificationBuilder.build())
    }

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
        download()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }



    //todo: реализовать загрузку архива и его распаковку
    private fun download() {
        val file = File(this.filesDir, "test.mp4")

        val url = URL("http://commonsware.com/misc/test.mp4")

        Thread(Runnable {
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                Log.d("MyTAG", "Server returned HTTP " + connection.responseCode
                        + " " + connection.responseCode)

            }

            val fos: FileOutputStream = FileOutputStream(file)
            val inputStream: InputStream = connection.inputStream




            val buffer = ByteArray(1024)
            var count: Int
            while ((count = inputStream.read(buffer)) != -1){ //fixme: !!! выдает ошибку!!!
                fos.write(buffer,0,count)
            }

            //вариант на Java
            /*byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }*/
        })




    }
}
