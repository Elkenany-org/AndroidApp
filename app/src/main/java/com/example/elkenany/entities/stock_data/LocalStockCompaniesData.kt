package com.example.elkenany.entities.stock_data

import com.squareup.moshi.Json

data class LocalStockCompaniesData(
    val data: List<LocalStockCompanyDaum?>,
)

data class LocalStockCompanyDaum(
    val id: Long?,
    val name: String?,
    val selected: Long?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "image_thum_url")
    val imageThumUrl: String?,
)