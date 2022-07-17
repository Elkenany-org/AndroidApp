package com.example.elkenany.entities.auth_data

import com.squareup.moshi.Json

data class AuthData(
    val name: String?,
    val email: String?,
    val phone: String?,
    @Json(name = "api_token")
    val apiToken: String?,
)