package com.cloniamix.lesson_9_engurazov_kotlin.network

import com.cloniamix.lesson_9_engurazov_kotlin.network.pojo.CityWeather
import io.reactivex.Single
import retrofit2.http.GET

interface WeatherApi {

    @GET("/data/2.5/weather?q=saransk&units=metric&appid=a924f0f5b30839d1ecb95f0a6416a0c2")
    fun getWeather(): Single<CityWeather>
}