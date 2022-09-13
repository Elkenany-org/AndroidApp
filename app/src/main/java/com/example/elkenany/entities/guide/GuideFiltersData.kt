package com.example.elkenany.entities.guide

import com.squareup.moshi.Json

data class GuideFiltersData(
    val sectors: List<Sector?>,
    @Json(name = "sub_sections")
    val subSections: List<SubSection?>,
    val countries: List<Country?>,
    val cities: List<City?>,
    val sort: List<Sort?>,
)

data class SubSection(
    val id: Long?,
    val name: String?,
)

data class Country(
    val id: Long?,
    val name: String?,
    val selected: Long?,
)


data class Sort(
    val id: Long?,
    val name: String?,
    val value: Long?,
)
