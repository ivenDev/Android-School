package com.cloniamix.lesson_9_engurazov_kotlin

import com.cloniamix.lesson_9_engurazov_kotlin.network.pojo.CityWeather

interface ServiceCallbacks {
    fun setWeather(cityWeather: CityWeather)
    fun error(message: String?)
}