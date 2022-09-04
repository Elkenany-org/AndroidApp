package com.example.elkenany.entities.stock_data

import com.squareup.moshi.Json

data class StatisticsFodderData(
    @Json(name = "list_members")
    val listMembers: List<FodderListMember?>,
    @Json(name = "changes_members")
    val changesMembers: List<FodderChangesMember?>,
)

data class FodderListMember(
    val id: Long?,
    val name: String?,
)

data class FodderChangesMember(
    val id: Long?,
    val name: String?,
    val categorize: String?,
    val compId: Long?,
    val changes: List<FodderChange?>,
    val change: String?,
    val counts: Long?,
)

data class FodderChange(
    val date: String?,
    val price: Long?,
)