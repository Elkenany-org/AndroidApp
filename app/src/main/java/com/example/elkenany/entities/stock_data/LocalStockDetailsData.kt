package com.example.elkenany.entities.stock_data

import com.squareup.moshi.Json

data class LocalStockDetailsData(
    val message: String?,
    val status: String?,
    val columns: ColumnsData?,
    val banners: List<LocalStockBanner?>,
    val logos: List<LocalStockLogo?>,
    val members: List<ColumnsData?>,
)

data class ColumnsData(
    val name: String?,
    val price: String?,
    val change: String?,
    @Json(name = "charging_system")
    val chargingSystem: String?,
    @Json(name = "categorize_name")
    val categorizeName: String?,
    val weight: String?,
    @Json(name = "price_status")
    val priceStatus: String?,
    val image: String?,
    val feed: String?,
    val age: String?,
    @Json(name = "product_type")
    val productType: String?,
    @Json(name = "chick_type")
    val chickType: String?,
    @Json(name = "weight_container")
    val weightContainer: String?,
    val statistics: String?,
    @Json(name = "mem_id")
    val memId: String?,
    val kind: String?,
    @Json(name = "change_date")
    val changeDate: String?,
    val type: String?,
)
