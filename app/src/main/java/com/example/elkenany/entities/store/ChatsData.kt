package com.example.elkenany.entities.store

import com.squareup.moshi.Json

data class ChatsData(
    val chat: List<Chat?>,
)

data class StartChatData(
    val chat: StartChatDaum?,
)

data class StartChatDaum(
    val id: Long?,
    @Json(name = "massages")
    val messages: List<Chat?>,
)

data class Chat(
    @Json(name = "massage")
    val message: String?,
    val id: Long?,
    @Json(name = "created_at")
    val createdAt: String?,
    val image: String?,
    val name: String?,
)


