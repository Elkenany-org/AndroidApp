package com.elkenany.entities.common

import com.squareup.moshi.Json

data class LogosAndBannersData(
    val id: Long?,
    val link: String?,
    val image: String?,
    @Json(name = "company_id")
    val companyId: Long?,
    @Json(name = "company_name")
    val companyName: String?,
    val type: String?,
)