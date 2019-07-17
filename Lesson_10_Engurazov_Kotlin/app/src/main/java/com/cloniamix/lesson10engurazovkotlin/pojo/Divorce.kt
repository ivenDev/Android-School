package com.cloniamix.lesson10engurazovkotlin.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Divorce(@SerializedName("end") val end: Date,
                   @SerializedName("id") val id: Int,
                   @SerializedName("start") val start: Date) : Parcelable