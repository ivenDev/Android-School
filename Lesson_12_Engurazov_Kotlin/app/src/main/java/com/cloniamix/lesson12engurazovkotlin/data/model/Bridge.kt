package com.cloniamix.lesson12engurazovkotlin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bridge(
    @SerializedName("description") val description: String,
    @SerializedName("description_eng") val descriptionEng: String,
    @SerializedName("divorces") val divorces: List<Divorce>,
    @SerializedName("id") val id: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("name") val name: String,
    @SerializedName("name_eng") val nameEng: String,
    @SerializedName("photo_close") val photoClose: String,
    @SerializedName("photo_open") val photoOpen: String,
    @SerializedName("public") val _public: Boolean,
    @SerializedName("resource_uri") val resourceUri: String
) : Parcelable