package com.cloniamix.lesson10engurazovkotlin.pojo

import com.google.gson.annotations.SerializedName

class Bridges(@SerializedName("meta") val meta: Meta,
              @SerializedName("objects") val objects: ArrayList<Bridge>)