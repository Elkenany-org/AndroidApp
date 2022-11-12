package com.elkenany.entities.tenders

import com.elkenany.entities.stock_data.GeneralBannerData
import com.elkenany.entities.stock_data.GeneralLogoData
import com.squareup.moshi.Json

data class TendersDetailsData(
    val banners: List<GeneralBannerData?>,
    val logos: List<GeneralLogoData?>,
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