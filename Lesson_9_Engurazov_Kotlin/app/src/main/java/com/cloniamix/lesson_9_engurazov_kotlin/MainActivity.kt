package com.cloniamix.lesson_9_engurazov_kotlin

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import com.cloniamix.lesson_9_engurazov_kotlin.network.pojo.CityWeather
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ServiceCallbacks{

    private var weatherService: WeatherService? = null
    private var isBound = false

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
            if (it.itemId == R.id.itemDownload) {Toast.makeText(this, "download", Toast.LENGTH_SHORT).show()}
            true
        }
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

    private fun updateUi(cityWeather: CityWeather){
        textViewCityName.text = cityWeather.name
        val temp = "${cityWeather.main.temp} C"//fixme: добавить знак градусов
        textViewTemperature.text = temp

        val dateString = DateFormat.format("HH.mm.ss", System.currentTimeMillis()).toString()
        textViewTime.text = dateString
    }


    override fun setWeather(cityWeather: CityWeather) {
        Log.d("MyTAG", "MainActivity: ${cityWeather.name}")
        updateUi(cityWeather)
    }

    override fun error(message: String?) {
        Log.d("MyTAG", "MainActivity: $message")
    }
}
