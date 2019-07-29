package com.cloniamix.lesson_9_engurazov_kotlin.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.AnyThread
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import com.cloniamix.lesson_9_engurazov_kotlin.MainActivity
import com.cloniamix.lesson_9_engurazov_kotlin.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipInputStream


class DownloadService : Service() {

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, DownloadService::class.java)
        }

        private const val CHANNEL_ID = "com.cloniamix.lesson_9_engurazov_kotlin.services"
        private const val FILE_URL = "https://dlemail.ru/pic.zip"

        const val STATUS_PROGRESS = "progress"
        const val STATUS_UNZIP = "unzip"
        const val STATUS_FINISH = "finish"
        const val STATUS_ERROR = "error"
    }

    private var disposable: Disposable? = null

    override fun onCreate() {
        super.onCreate()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
        val notificationBuilder = createNotificationBuilder(this, CHANNEL_ID)
        startForeground(1, notificationBuilder.build())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(CHANNEL_ID, "DownloadServiceChannel", NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }


    private fun createNotificationBuilder(context: Context, channelId: String): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, channelId)
        builder.setSmallIcon(R.drawable.ic_file_download)
        builder.setContentTitle(getString(R.string.notification_title_text))
        builder.priority = NotificationCompat.PRIORITY_LOW
        builder.setContentText(getString(R.string.notification_content_text))
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

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun download() {

        disposable = Single.fromCallable { downloadAndUnzip() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ stopSelf() }, { stopSelf() })
    }

    @WorkerThread
    private fun downloadAndUnzip() {
        val zipFile = downloadZipFile()

        var intent = Intent(MainActivity.MY_ACTION)
        intent.putExtra(STATUS_UNZIP, "Распаковка...")
        sendBroadcast(intent)

        val unzipFile = unzip(zipFile)

        intent = Intent(MainActivity.MY_ACTION)
        if (unzipFile != null) {
            intent.putExtra(STATUS_FINISH, unzipFile.name)
        } else {
            intent.putExtra(STATUS_ERROR, "Ошибка файла")
        }
        sendBroadcast(intent)
    }

    @WorkerThread
    private fun unzip(zipFile: File?): File? {
        if (zipFile != null) {
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

        return null
    }

    @AnyThread
    private fun downloadZipFile(): File? {

        val url = URL(FILE_URL)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        val fileSize = connection.contentLength

        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            Log.d(
                "MyTAG", "Server returned HTTP " + connection.responseCode
            )

        } else {
            val zipFile = File(this.filesDir, "pic_zip.zip")
            val fos = FileOutputStream(zipFile)
            val inputStream: InputStream = connection.inputStream

            val buffer = ByteArray(1024)
            var count: Int = inputStream.read(buffer)
            var total: Long = 0
            while (count != -1) {
                total += count

                val intent = Intent(MainActivity.MY_ACTION)
                intent.putExtra(STATUS_PROGRESS, ((total * 100) / fileSize))
                sendBroadcast(intent)

                fos.write(buffer, 0, count)
                count = inputStream.read(buffer)
            }

            fos.close()
            inputStream.close()
            connection.disconnect()
            return zipFile
        }
        return null
    }
}
