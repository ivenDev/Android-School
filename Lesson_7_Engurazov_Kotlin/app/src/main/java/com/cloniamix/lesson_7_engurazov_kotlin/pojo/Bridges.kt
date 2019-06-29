package com.cloniamix.lesson_7_engurazov_kotlin.pojo

import com.google.gson.annotations.SerializedName

class Bridges(@SerializedName("meta") val meta: Meta,
              @SerializedName("objects") val objects: ArrayList<Bridge>)