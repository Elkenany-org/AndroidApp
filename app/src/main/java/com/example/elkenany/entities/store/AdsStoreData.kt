package com.example.elkenany.entities.store

import com.example.elkenany.entities.stock_data.LocalStockSector
import com.squareup.moshi.Json

data class AdsStoreData(
    @Json(name = "sectors")
    val sectors: List<LocalStockSector?>,
    val banners: List<Any?>,
    val logos: List<Any?>,
    val sort: List<Any?>,
    val data: List<AdsStoreDaum>,
    @Json(name = "current_page")
    val currentPage: Long?,
    @Json(name = "last_page")
    val lastPage: Long?,
    @Json(name = "first_page_url")
    val firstPageUrl: String?,
    @Json(name = "next_page_url")
    val nextPageUrl: Any?,
    @Json(name = "last_page_url")
    val lastPageUrl: String?,
)


data class AdsStoreDaum(
    val id: Long?,
    val title: String?,
    val salary: Long?,
    val address: String?,
    val image: String?,
    @Json(name = "created_at")
    val createdAt: String?,
)