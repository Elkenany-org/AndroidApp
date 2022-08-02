package com.example.elkenany.entities.home_data

import com.squareup.moshi.Json

data class NotificationsData(
    @Json(name = "nots")
    val notifications: List<Nots>?,
)

data class Nots(
    val id: String?,
    val title: String?,
    val desc: String?,
    val image: String?,
    @Json(name = "product_id")
    val productId: Any?,
    @Json(name = "product_name")
    val productName: Any?,
    @Json(name = "product_image")
    val productImage: Any?,
)