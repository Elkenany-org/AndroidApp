package com.example.elkenany.entities.guide

import com.example.elkenany.entities.stock_data.LocalStockBanner
import com.example.elkenany.entities.stock_data.LocalStockLogo
import com.squareup.moshi.Json

data class CompaniesData(
    val sectors: List<Sector?>,
    val banners: List<LocalStockBanner?>,
    val logos: List<LocalStockLogo?>,
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
    val sponsor : Int?
)