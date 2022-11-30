package com.elkenany.entities.tenders

import com.elkenany.entities.common.LogosAndBannersData
import com.squareup.moshi.Json

data class TendersListData(
    val sections: List<TendersSectionsDaum?>,
    val banners: List<LogosAndBannersData?>,
    val logos: List<LogosAndBannersData?>,
    val data: List<TendersDaum?>,
    @Json(name = "current_page")
    val currentPage: Long?,
    @Json(name = "last_page")
    val lastPage: Long?,
)

data class TendersDaum(
    val id: Long?,
    val title: String?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
)

data class TendersSectionsDaum(
    val id: Long?,
    val name: String?,
    val type: String?,
    val selected: Long?,
)