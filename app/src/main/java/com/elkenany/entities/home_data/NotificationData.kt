package com.elkenany.entities.home_data

import com.squareup.moshi.Json

data class NotificationsData(
    @Json(name = "result")
    val notifications: List<Nots?>,
)

data class Nots(
    val id: Long?,
    val title: String?,
    val desc: String?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "key_name")
    val keyName: String?,
    @Json(name = "key_id")
    val keyId: Long?,
)