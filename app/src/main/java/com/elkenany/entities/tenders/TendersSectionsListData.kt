package com.elkenany.entities.tenders


data class TendersSectionsListData(
    val sections: List<TendersSection?>,
)

data class TendersSection(
    val id: Long?,
    val name: String?,
    val image: String?,
)