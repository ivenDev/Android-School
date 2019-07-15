package com.cloniamix.lesson_9_engurazov_kotlin.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.content.Context
import android.util.Log
import com.cloniamix.lesson_9_engurazov_kotlin.MainActivity
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipInputStream


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


    private fun download() {

        Thread(Runnable {
            val zipFile = downloadZipFile()

            var intent = Intent(MainActivity.MY_ACTION)
            intent.putExtra("unzip", "Распаковка..." )
            sendBroadcast(intent)

            val unzipFile = unzip(zipFile)

            intent = Intent(MainActivity.MY_ACTION)
            intent.putExtra("finish", unzipFile.name)
            sendBroadcast(intent)

            stopSelf()

        }).start()

    }

    private fun unzip(zipFile: File): File {
        val fileInputStream = FileInputStream(zipFile)
        val unzipFile = File(this.filesDir, "pic_unzip.jpg")
        val outputStream = FileOutputStream(unzipFile)
        val zipInputStream = ZipInputStream(BufferedInputStream(fileInputStream))

        while (zipInputStream.nextEntry != null) {
            val buffer = ByteArray(1024)

            var count: Int = zipInputStream.read(buffer)
            while (count > -1) {
                outputStream.write(buffer, 0, count)
                count = zipInputStream.read(buffer)
            }
            outputStream.close()
            zipInputStream.closeEntry()
        }


        zipInputStream.close()
        fileInputStream.close()

        return unzipFile
    }

    private fun downloadZipFile(): File {

        val zipFile = File(this.filesDir, "pic_zip.zip")

        val url = URL("https://getfile.dokpub.com/yandex/get/https://yadi.sk/d/roTxWVw6YODhvQ")
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        val fileSize = connection.contentLength

        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            Log.d(
                "MyTAG", "Server returned HTTP " + connection.responseCode
                        + " " + connection.responseCode
            )

        }

        val fos = FileOutputStream(zipFile)
        val inputStream: InputStream = connection.inputStream


        val buffer = ByteArray(1024)
        var count: Int = inputStream.read(buffer)
        var total: Long = 0
        while (count != -1) {
            total += count

            val intent = Intent(MainActivity.MY_ACTION)
            intent.putExtra("progress", ((total * 100) / fileSize))
            sendBroadcast(intent)

            fos.write(buffer, 0, count)
            count = inputStream.read(buffer)
        }

        fos.close()
        inputStream.close()

        return zipFile
    }
}
