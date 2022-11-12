package com.elkenany.entities.recruitment

data class CompaniesListData(
    val result: List<CompaniesFilterData>?,
)

data class CompaniesFilterData(
    val id: Long?,
    val name: String?,
)