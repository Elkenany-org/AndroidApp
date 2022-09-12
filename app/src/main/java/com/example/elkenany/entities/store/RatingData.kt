package com.example.elkenany.entities.store

import com.squareup.moshi.Json

data class RatingData(
    val rate: String?,
    @Json(name = "company_id")
    val companyId: String?,
    @Json(name = "user_id")
    val userId: Long?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val id: Long?,
)