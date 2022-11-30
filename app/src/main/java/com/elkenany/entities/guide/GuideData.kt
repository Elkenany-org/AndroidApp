package com.elkenany.entities.guide

import com.elkenany.entities.common.LogosAndBannersData
import com.elkenany.entities.stock_data.LocalStockLogoIn
import com.elkenany.entities.stock_data.LocalStockSector
import com.squareup.moshi.Json

data class GuideData(
    val sectors: List<LocalStockSector?>,
    val banners: List<LogosAndBannersData?>,
    val logos: List<LogosAndBannersData?>,
    @Json(name = "sub_sections")
    val subSections: List<GuideSubSection?>,
)

data class GuideSubSection(
    val id: Long?,
    val name: String?,
    val image: String?,
    @Json(name = "companies_count")
    val companiesCount: Long?,
    @Json(name = "logo_in")
    val logoIn: List<LocalStockLogoIn?>,
)
