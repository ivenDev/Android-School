package com.cloniamix.lesson12engurazovkotlin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Класс-модель объекта мост
 *
 * @param description информация о мосте
 * @param descriptionEng информация о мосте на английском языке
 * @param divorces время, когда мост будет разведен
 * @param id id моста
 * @param lat координата широты моста
 * @param lng координата долготы моста
 * @param name название моста
 * @param nameEng название моста на английском
 * @param photoClose url фотографии разведенного моста
 * @param photoOpen url фотографии сведенного моста
 * */

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