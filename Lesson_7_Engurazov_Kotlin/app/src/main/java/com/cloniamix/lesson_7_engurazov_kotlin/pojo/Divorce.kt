package com.cloniamix.lesson_7_engurazov_kotlin.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Divorce(@SerializedName("end") val end: String,
              @SerializedName("id") val id: Int,
              @SerializedName("start") val start: String) : Parcelable