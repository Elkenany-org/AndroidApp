package com.elkenany.entities.guide

import com.elkenany.entities.stock_data.GeneralBannerData
import com.elkenany.entities.stock_data.GeneralLogoData
import com.elkenany.entities.stock_data.LocalStockLogoIn
import com.elkenany.entities.stock_data.LocalStockSector
import com.squareup.moshi.Json

data class GuideData(
    val sectors: List<LocalStockSector?>,
    val banners: List<GeneralBannerData?>,
    val logos: List<GeneralLogoData?>,
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
