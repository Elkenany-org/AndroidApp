package com.elkenany.entities.shows_data


import com.elkenany.entities.common.LogosAndBannersData
import com.squareup.moshi.Json

data class ShowReviewersData(
    val banners: List<LogosAndBannersData?>,
    val logos: List<LogosAndBannersData?>,
    val review: List<Review?>,
    val rate: Double?,
)

data class Review(
    val name: String?,
    val email: String?,
    val desc: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val rate: Long?,
    val id: Long?,
)