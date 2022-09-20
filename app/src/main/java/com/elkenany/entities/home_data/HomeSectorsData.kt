package com.elkenany.entities.home_data

import com.squareup.moshi.Json

data class HomeSectorsData(
    @Json(name = "sectors")
    val sectors: List<Sector>?,
    @Json(name = "logos")
    val sectorsLogos: List<SectorsLogo>?,
    @Json(name = "popup")
    val sectorsPopup: SectorsPopup?,
    val type: String?,
    @Json(name = "recomandtion")
    val sectorsRecomandtion: List<SectorsRecomandtion>?,
    @Json(name = "guide")
    val sectorsGuide: List<SectorsGuide>?,
    @Json(name = "stock")
    val sectorsStock: List<SectorsStock>?,
    @Json(name = "news")
    val sectorsNews: List<SectorsNews>?,
    @Json(name = "store")
    val sectorsStore: List<SectorsStore>?,
)


data class Sector(
    val id: Long?,
    val name: String?,
    val type: String?,
    val image: String?,
)

data class SectorsLogo(
    val id: Long?,
    val link: String?,
    val image: String?,
)

data class SectorsPopup(
    val id: Long?,
    val link: String?,
    val media: String?,
)

data class SectorsRecomandtion(
    val id: Long?,
    val name: String?,
    val type: String?,
    val image: String?,
    @Json(name = "companies_count")
    val companiesCount: Long?,
    val members: Long?,
)

data class SectorsGuide(
    val id: Long?,
    val name: String?,
    val type: String?,
    val image: String?,
    @Json(name = "companies_count")
    val companiesCount: Long?,
)

data class SectorsStock(
    val id: Long?,
    val name: String?,
    val type: String?,
    val image: String?,
    val members: Long?,
)

data class SectorsNews(
    val id: Long?,
    val name: String?,
    val type: String?,
    val image: String?,
)

data class SectorsStore(
    val id: Long?,
    val name: String?,
    val type: String?,
    val image: String?,
)