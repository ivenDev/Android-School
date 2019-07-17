package com.cloniamix.lesson_9_engurazov_kotlin

import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cloniamix.lesson_9_engurazov_kotlin.network.pojo.CityWeather
import com.cloniamix.lesson_9_engurazov_kotlin.services.DownloadService
import com.cloniamix.lesson_9_engurazov_kotlin.services.WeatherService
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), ServiceCallbacks {

    companion object {
        const val MY_ACTION = "com.cloniamix.lesson_9_engurazov_kotlin.TEST_ACTION"
    }

    private var weatherService: WeatherService? = null
    private var isBound = false

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            setProgressStatus(intent)
        }
    }

    private fun setProgressStatus(intent: Intent) {
        var progressText = ""
        if (intent.hasExtra(DownloadService.STATUS_PROGRESS)) {
            progressText = "Progress: " + intent.getLongExtra(DownloadService.STATUS_PROGRESS, 0) + "%"
        }
        if (intent.hasExtra(DownloadService.STATUS_UNZIP)) {
            progressText = intent.getStringExtra(DownloadService.STATUS_UNZIP)
        }

        if (intent.hasExtra(DownloadService.STATUS_ERROR)) {
            progressText = intent.getStringExtra(DownloadService.STATUS_ERROR)
        }

        if (intent.hasExtra(DownloadService.STATUS_FINISH)) {
            progressText = intent.getStringExtra(DownloadService.STATUS_FINISH)

            Log.d("MyTAG", progressText + filesDir.name)

            val image = File(filesDir, progressText)

            Glide.with(this@MainActivity)
                .load(image)
                .placeholder(R.drawable.ic_crop_original)
                .centerCrop()
                .into(imageView)
        }

        Log.d("MyTAG", progressText)
        textViewProgress.text = progressText
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as WeatherService.MyBinder
            weatherService = binder.getService()
            weatherService?.setCallbacks(this@MainActivity)
            isBound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRefresh.setOnClickListener { weatherService?.refreshData() }

        toolbarMainActivity.inflateMenu(R.menu.main_menu)
        toolbarMainActivity.setOnMenuItemClickListener {
            if (it.itemId == R.id.itemDownload) {
                startDownloadService()
                Toast.makeText(this, getString(R.string.download_start_toast_text), Toast.LENGTH_SHORT).show()
            }
            true
        }

        val intFilter = IntentFilter(MY_ACTION)
        registerReceiver(broadcastReceiver, intFilter)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, WeatherService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            weatherService?.setCallbacks(null)
            unbindService(serviceConnection)
            isBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun updateUi(cityWeather: CityWeather) {
        textViewCityName.text = cityWeather.name
        val temp = "${cityWeather.main.temp} C"
        textViewTemperature.text = temp

        val dateString = DateFormat.format("HH.mm.ss", System.currentTimeMillis()).toString()
        textViewTime.text = dateString
    }

    private fun startDownloadService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(DownloadService.createStartIntent(this))
        } else {
            startService(DownloadService.createStartIntent(this))
        }
    }


    override fun setWeather(cityWeather: CityWeather) {
        Log.d("MyTAG", "MainActivity: ${cityWeather.name}")
        showWhenError(false)
        updateUi(cityWeather)
    }

    override fun error(message: String?) {
        Log.d("MyTAG", "MainActivity: $message")
        showWhenError(true)
    }

    private fun showWhenError(isError: Boolean) {
        when (isError) {
            true -> {
                linearLayoutWeatherContainer.visibility = View.GONE
                buttonRefresh.visibility = View.VISIBLE
            }
            false -> {
                linearLayoutWeatherContainer.visibility = View.VISIBLE
                buttonRefresh.visibility = View.GONE
            }
        }

    }
}
