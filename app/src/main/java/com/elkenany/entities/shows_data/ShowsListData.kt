package com.elkenany.entities.shows_data

import com.elkenany.entities.stock_data.GeneralBannerData
import com.elkenany.entities.stock_data.GeneralLogoData
import com.elkenany.entities.stock_data.LocalStockSector
import com.squareup.moshi.Json

data class ShowsListData(
    val sectors: List<LocalStockSector?>,
    val banners: List<GeneralBannerData?>,
    val logos: List<GeneralLogoData?>,
    val data: List<ShowsData?>,
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


data class ShowsData(
    val id: Long?,
    val name: String?,
    val rate: Double?,
    val image: String?,
    val desc: String?,
    val address: String?,
    @Json(name = "view_count")
    val viewCount: Long?,
    val date: String?,
    @Json(name = "going_state")
    val goingState: Any?,
    @Json(name = "deeb_link")
    val deebLink: String?,
    val link: String?,
)
