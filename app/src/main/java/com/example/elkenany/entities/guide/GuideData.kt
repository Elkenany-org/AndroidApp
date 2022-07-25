package com.example.elkenany.entities.guide

import com.example.elkenany.entities.stock_data.LocalStockBanner
import com.example.elkenany.entities.stock_data.LocalStockLogo
import com.example.elkenany.entities.stock_data.LocalStockLogoIn
import com.example.elkenany.entities.stock_data.LocalStockSector
import com.example.elkenany.views.local_stock.adapter.LocalStockLogosAdapter
import com.squareup.moshi.Json

data class GuideData(
    val sectors: List<LocalStockSector?>,
    val banners: List<LocalStockBanner?>,
    val logos: List<LocalStockLogo?>,
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
