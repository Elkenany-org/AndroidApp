package com.elkenany.entities.store

import com.squareup.moshi.Json

data class AdsDetailsData(
    val banners: List<Any?>,
    val logos: List<Any?>,
    val id: Long?,
    val title: String?,
    val salary: Long?,
    val phone: String?,
    @Json(name = "view_count")
    val viewCount: Long?,
    val address: String?,
    val paid: String?,
    val user: String?,
    val type: String?,
    val desc: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val images: List<AdsImage?>,
)

data class AdsImage(
    val id: Long?,
    val image: String?,
)