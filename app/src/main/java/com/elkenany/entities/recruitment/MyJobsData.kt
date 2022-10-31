package com.elkenany.entities.recruitment

import com.squareup.moshi.Json

data class MyJobsData(
    val jobs: List<MyJobDaum?>,
)

data class MyJobDaum(
    val id: Long?,
    val title: String?,
    val salary: Long?,
    val address: String?,
    val desc: String?,
    val category: String?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val status: String?,
    @Json(name = "application_status")
    val applicationStatus: String?,
    val favorite: Int?,
)