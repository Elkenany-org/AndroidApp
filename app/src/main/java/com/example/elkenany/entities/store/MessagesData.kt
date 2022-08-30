package com.example.elkenany.entities.store

import com.squareup.moshi.Json

data class MessagesData(
    val chat: MessagesList?,
)

data class MessagesList(
    val massages: List<Massage?>,
)

data class Massage(
    val id: Long?,
    val image: String?,
    val name: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "massage")
    val message: String?,
)