package com.example.elkenany.entities.stock_data

import com.squareup.moshi.Json


data class StatisticsData(
    @Json(name = "list_members")
    val listMembers: List<ListMember?>,
    @Json(name = "changes_members")
    val changesMembers: List<ChangesMember?>,
)

data class ListMember(
    val id: Long?,
    val name: String?,
)

data class ChangesMember(
    val id: Long?,
    val name: String?,
    val changes: List<Change?>,
    val change: String?,
    val counts: Long?,
)

data class Change(
    val date: String?,
    val price: Long?,
)