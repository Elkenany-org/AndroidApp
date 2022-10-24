package com.elkenany.entities.recruitment

import com.elkenany.entities.stock_data.GeneralBannerData
import com.squareup.moshi.Json

data class JobsData(
    val banners: List<GeneralBannerData?>,
    val jobs: List<JobDaum?>,
    val categories: List<Category?>,
)

data class JobDaum(
    val id: Long?,
    val title: String?,
    val salary: Long?,
    val address: String?,
    val desc: String?,
    val phone: String?,
    val email: String?,
    val experience: Long?,
    val category: String?,
    @Json(name = "recruiter_name")
    val recruiterName: String?,
    @Json(name = "company_id")
    val companyId: Long?,
    @Json(name = "company_name")
    val companyName: String?,
    val image: String?,
    @Json(name = "sector_id")
    val sectorId: Long?,
    @Json(name = "sector_name")
    val sectorName: String?,
    @Json(name = "sector_type")
    val sectorType: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val type: String?,
)

data class Category(
    val id: Long?,
    val selected: Long?,
    val name: String?,
)