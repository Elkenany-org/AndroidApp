package com.elkenany.entities.shows_data


import com.elkenany.entities.common.LogosAndBannersData
import com.squareup.moshi.Json

data class ShowsDetailsData(
    val banners: List<LogosAndBannersData?>,
    val logos: List<LogosAndBannersData?>,
    val id: Long?,
    val name: String?,
    @Json(name = "short_desc")
    val shortDesc: String?,
    @Json(name = "view_count")
    val viewCount: Long?,
    val address: String?,
    val rate: Double?,
    @Json(name = "count_Showers")
    val countShowers: Long?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val dates: List<ShowsDate?>,
    val times: List<ShowsTime?>,
    val tickets: List<ShowsTicket?>,
    val images: List<ShowsImage?>,
    val organisers: List<ShowsOrganiser?>,
)

data class ShowsDate(
    val date: String?,
)

data class ShowsTime(
    val time: String?,
)

data class ShowsTicket(
    val status: String?,
    val price: Long?,
)

data class ShowsImage(
    val image: String?,
    val id: Long?,
)

data class ShowsOrganiser(
    val name: String?,
    val id: Long?,
)