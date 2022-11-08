package com.elkenany.entities.recruitment

data class JobDepartmentsData(
    val categories: List<CategoryDaum>?,
    val sort: List<Sort>?,
)

data class CategoryDaum(
    val id: Long?,
    val name: String?,
)

data class Sort(
    val id: Long?,
    val name: String?,
    val value: String?,
)