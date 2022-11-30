package com.elkenany.entities.tenders

import com.elkenany.entities.common.LogosAndBannersData
import com.squareup.moshi.Json

data class TendersDetailsData(
    val banners: List<LogosAndBannersData?>,
    val logos: List<LogosAndBannersData?>,
    val id: Long?,
    val title: String?,
    val image: String?,
    val desc: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val tenders: List<MoreTenders?>,
)

data class MoreTenders(
    val id: Long?,
    val title: String?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
)