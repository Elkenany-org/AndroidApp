package com.example.elkenany.entities.stock_data

import com.squareup.moshi.Json

data class LocalStockDetailsData(
    @Json(name = "columns")
    val columns: List<Column?>,
    @Json(name = "banners")
    val banners: List<LocalStockBanner?>,
    @Json(name = "logos")
    val logos: List<LocalStockLogo?>,
    @Json(name = "members")
    val members: List<Member?>,
)
data class Column(
    val title: String,
)
data class Member(
    val name: String,
    @Json(name = "mem_id")
    val memId: Long,
    val kind: String,
    val price: String,
    val change: String,
    @Json(name = "change_date")
    val changeDate: String,
    val statistics: String,
    @Json(name = "chick_type")
    val chickType: String,
    val type: Long,
)