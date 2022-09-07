package com.example.elkenany.entities.store

import com.example.elkenany.entities.stock_data.LocalStockSector
import com.squareup.moshi.Json

data class AdsStoreData(
    @Json(name = "sectors")
    val sectors: List<LocalStockSector?>,
    val banners: List<Any?>,
    val logos: List<Any?>,
    val data: List<AdsStoreDaum?>,
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