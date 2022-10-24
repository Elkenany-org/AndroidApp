package com.elkenany.entities.guide

import com.elkenany.entities.stock_data.GeneralBannerData
import com.elkenany.entities.stock_data.GeneralLogoData
import com.squareup.moshi.Json

data class CompaniesData(
    val sectors: List<Sector?>,
    val banners: List<GeneralBannerData?>,
    val logos: List<GeneralLogoData?>,
    val compsort: List<CompaniesDaum?>,
    val data: List<CompaniesDaum?>,
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

data class Sector(
    val id: Long?,
    val name: String?,
    val type: String?,
    val selected: Long?,
)

data class CompaniesDaum(
    val id: Long?,
    val name: String?,
    val rate: Double?,
    val image: String?,
    val desc: String?,
    val address: String?,
    val sponser : Int?
)