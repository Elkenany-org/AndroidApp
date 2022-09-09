package com.example.elkenany.entities.stock_data

import com.squareup.moshi.Json

data class LocalStockFeedsData(
    val data: FeedsData?,
)

data class FeedsData(
    @Json(name = "fodder_categories")
    val fodderCategories: List<FodderCategory?>,
    @Json(name = "fodder_list")
    val fodderList: List<FodderList?>,
)

data class FodderCategory(
    val id: Long?,
    val name: String?,
    val selected: Long?,
)

data class FodderList(
    val id: Long?,
    val name: String?,
    val selected: Long?,
)