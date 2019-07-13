package com.cloniamix.lesson_9_engurazov_kotlin.network.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("main")
    @Expose
    val main: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("icon")
    @Expose
    val icon: String
)