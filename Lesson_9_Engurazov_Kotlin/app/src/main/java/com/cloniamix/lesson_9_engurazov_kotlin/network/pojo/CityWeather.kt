package com.cloniamix.lesson_9_engurazov_kotlin.network.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityWeather (@SerializedName("weather")
                        @Expose
                        var weather: List<Weather>?  = null,

                        @SerializedName("main")
                        @Expose
                        val main: Main,

                        @SerializedName("dt")
                        @Expose
                        val dt: Long,

                        @SerializedName("name")
                        @Expose
                        val name: String

)






    /*@SerializedName("coord")
    @Expose
    private Coord coord;*/

    ;

    /*@SerializedName("base")
    @Expose
    private String base;*/

   ;

    /*@SerializedName("wind")
    @Expose
    private Wind wind;

    @SerializedName("rain")
    @Expose
    private Rain rain;

    @SerializedName("clouds")
    @Expose
    private Clouds clouds;*/

    ;

    /*@SerializedName("sys")
    @Expose
    private Sys sys;

    @SerializedName("timezone")
    @Expose
    private Integer timezone;*/

   /* @SerializedName("id")
    @Expose
    private Integer id;*/

    ;

    /*@SerializedName("cod")
    @Expose
    private Integer cod;*/