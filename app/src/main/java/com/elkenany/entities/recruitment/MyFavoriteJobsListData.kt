package com.elkenany.entities.recruitment

import com.squareup.moshi.Json

data class MyFavoriteJobsListData(
    val jobs: List<FavoriteJob?>,
)

data class FavoriteJob(
    val id: Long?,
    val title: String?,
    val salary: Long?,
    val address: String?,
    val desc: String?,
    val phone: String?,
    val email: String?,
    val experience: String?,
    val category: String?,
    @Json(name = "recruiter_name")
    val recruiterName: String?,
    @Json(name = "company_id")
    val companyId: Long?,
    @Json(name = "company_name")
    val companyName: String?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val type: String?,
)