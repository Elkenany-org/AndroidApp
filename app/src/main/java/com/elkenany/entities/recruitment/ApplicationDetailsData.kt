package com.elkenany.entities.recruitment

import com.squareup.moshi.Json

data class ApplicationDetailsData(
    val application: ApplicationDaum?,
)

data class ApplicationDaum(
    val id: Long?,
    val name: String?,
    val email: String?,
    val phone: String?,
    @Json(name = "notice_period")
    val noticePeriod: String?,
    val education: String?,
    val experience: String?,
    @Json(name = "expected_salary")
    val expectedSalary: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    val cv: String?,
    val image: String?,
    @Json(name = "other_info")
    val otherInfo: String?,
    val qualified: Int?,
)