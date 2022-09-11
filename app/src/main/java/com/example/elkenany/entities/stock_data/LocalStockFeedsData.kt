package com.example.elkenany.entities.stock_data

import com.squareup.moshi.Json



data class FeedsData(
    @Json(name = "fodder_list")
    val fodderList: List<FodderList?>,
)


data class FodderList(
    val id: Long?,
    val name: String?,
)