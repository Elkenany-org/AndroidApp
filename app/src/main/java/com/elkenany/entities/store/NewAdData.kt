package com.elkenany.entities.store

import com.squareup.moshi.Json

data class NewAdData(
    @Json(name = "ad_detials")
    val adDetials: AdDetials?,
)

data class AdDetials(
    val id: Long?,
    val title: String?,
    val desc: String?,
    val phone: String?,
    val address: String?,
    @Json(name = "con_type")
    val conType: String?,
    val salary: String?,
    val images: List<Image?>,
)

data class Image(
    val id: Long?,
    val image: String?,
)