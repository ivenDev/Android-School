package com.cloniamix.lesson_9_engurazov_kotlin

import android.content.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import com.cloniamix.lesson_9_engurazov_kotlin.network.pojo.CityWeather
import com.cloniamix.lesson_9_engurazov_kotlin.services.DownloadService
import com.cloniamix.lesson_9_engurazov_kotlin.services.WeatherService
import kotlinx.android.synthetic.main.activity_main.*
import android.content.IntentFilter
import android.content.Intent
import android.content.BroadcastReceiver
import com.bumptech.glide.Glide
import java.io.File

class MainActivity : AppCompatActivity(), ServiceCallbacks{

    companion object {
        const val MY_ACTION = "com.cloniamix.lesson_9_engurazov_kotlin.TEST_ACTION"
    }

    private var weatherService: WeatherService? = null
    private var isBound = false

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            var progressText = ""
            if (intent.hasExtra("progress")){
                progressText = "Progress: " + intent.getIntExtra("progress",0) + "%"
            }
            if (intent.hasExtra("unzip")){
                progressText = intent.getStringExtra("unzip")
            }
            if (intent.hasExtra("finish")){
                progressText = intent.getStringExtra("finish")

                Log.d("MyTAG", progressText + filesDir.name )

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

        toolbarMainActivity.inflateMenu(R.menu.main_menu)
        toolbarMainActivity.setOnMenuItemClickListener {
            if (it.itemId == R.id.itemDownload) {
                startDownloadService()
                Toast.makeText(this, "download", Toast.LENGTH_SHORT).show()}
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
        if (isBound){
            weatherService?.setCallbacks(null)
            unbindService(serviceConnection)
            isBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun updateUi(cityWeather: CityWeather){
        textViewCityName.text = cityWeather.name
        val temp = "${cityWeather.main.temp} C"
        textViewTemperature.text = temp

        val dateString = DateFormat.format("HH.mm.ss", System.currentTimeMillis()).toString()
        textViewTime.text = dateString
    }

    private fun startDownloadService(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(DownloadService.createStartIntent(this))
        } else{
            startService(DownloadService.createStartIntent(this))
        }
    }


    override fun setWeather(cityWeather: CityWeather) {
        Log.d("MyTAG", "MainActivity: ${cityWeather.name}")
        updateUi(cityWeather)
    }

    override fun error(message: String?) {
        Log.d("MyTAG", "MainActivity: $message")
    }
}
