package com.elkenany.entities.ships

import com.elkenany.entities.stock_data.LocalStockBanner
import com.elkenany.entities.stock_data.LocalStockLogo
import com.squareup.moshi.Json

data class ShipsListData(
    val banners: List<LocalStockBanner?>,
    val logos: List<LocalStockLogo?>,
    val ships: List<Ship?>,
)

data class Ship(
    val id: Long?,
    val name: String?,
    val load: Long?,
    val product: String?,
    val country: String?,
    val date: String?,
    val company: String?,
    @Json(name = "Port")
    val port: String?,
    val agent: String?,
    @Json(name = "dir_date")
    val dirDate: String?,
)