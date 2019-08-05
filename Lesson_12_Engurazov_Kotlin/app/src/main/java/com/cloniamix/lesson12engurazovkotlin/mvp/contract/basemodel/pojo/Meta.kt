package com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo


import com.google.gson.annotations.SerializedName


class Meta(
    @SerializedName("limit") val limit: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("offset") val offset: Int,
    @SerializedName("previous") val previous: String?,
    @SerializedName("total_count") val totaCount: Int
)