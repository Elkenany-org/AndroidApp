package com.elkenany.entities.stock_data

import com.elkenany.entities.common.LogosAndBannersData
import com.squareup.moshi.Json

data class LocalStockData(
    @Json(name = "sectors")
    val sectors: List<LocalStockSector?>,
    @Json(name = "banners")
    val banners: List<LogosAndBannersData?>,
    @Json(name = "logos")
    val logos: List<LogosAndBannersData?>,
    @Json(name = "sub_sections")
    val subSections: List<LocalStockSubSection?>?,
    @Json(name = "fod_sections")
    val fodSections: List<LocalStockSubSection?>?,
)

data class LocalStockSector(
    val id: Long?,
    val name: String?,
    val type: String?,
    val selected: Long?,
)


data class LocalStockSubSection(
    val id: Long?,
    val name: String?,
    val image: String?,
    val members: Long?,
    val type: String?,
    @Json(name = "logo_in")
    val logoIn: List<LocalStockLogoIn?>,
)

data class LocalStockLogoIn(
    val id: Long?,
    val link: String?,
    val image: String?,
)


