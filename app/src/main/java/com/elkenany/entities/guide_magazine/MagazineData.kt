package com.elkenany.entities.guide_magazine

import com.elkenany.entities.common.LogosAndBannersData
import com.elkenany.entities.stock_data.LocalStockSector
import com.squareup.moshi.Json

data class MagazineData(
    val sectors: List<LocalStockSector?>,
    val banners: List<LogosAndBannersData?>,
    val logos: List<LogosAndBannersData?>,
    val data: List<MagazineDaum?>,
    @Json(name = "current_page")
    val currentPage: Long?,
    @Json(name = "last_page")
    val lastPage: Long?,
    @Json(name = "first_page_url")
    val firstPageUrl: String?,
    @Json(name = "next_page_url")
    val nextPageUrl: String?,
    @Json(name = "last_page_url")
    val lastPageUrl: String?,
)

data class MagazineDaum(
    val id: Long?,
    val name: String?,
    val rate: Double?,
    val image: String?,
    val desc: String?,
    val address: String?,
)