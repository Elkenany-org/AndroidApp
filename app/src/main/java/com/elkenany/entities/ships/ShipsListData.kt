package com.elkenany.entities.ships


import com.elkenany.entities.common.LogosAndBannersData
import com.squareup.moshi.Json

data class ShipsListData(
    val banners: List<LogosAndBannersData?>,
    val logos: List<LogosAndBannersData?>,
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