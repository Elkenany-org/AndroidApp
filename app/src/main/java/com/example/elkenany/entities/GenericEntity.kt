package com.example.elkenany.entities

import com.squareup.moshi.Json

data class GenericEntity<T>(
    @Json(name = "message")
    val message: String?,
    @Json(name = "error")
    val error: String?,
    val data: T?,
)