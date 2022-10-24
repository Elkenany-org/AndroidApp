package com.elkenany.entities.recruitment

import com.squareup.moshi.Json

data class JobDetailsData(
    val id: Long?,
    val title: String?,
    val salary: Long?,
    val phone: String?,
    @Json(name = "view_count")
    val viewCount: Long?,
    val address: String?,
    val desc: String?,
    val email: String?,
    val experience: String?,
    @Json(name = "company_id")
    val companyId: Long?,
    @Json(name = "company_name")
    val companyName: String?,
    val images: String?,
    val category: String?,
    val user: String?,
    @Json(name = "user_created_at")
    val userCreatedAt: String?,
    val type: String?,
    @Json(name = "created_at")
    val createdAt: String?,
)