package com.elkenany.entities.recruitment

import com.squareup.moshi.Json

data class ApplyData(
    val application: Application?,
)

data class Application(
    val id: Long?,
    val experience: String?,
    val education: String?,
    @Json(name = "expected_salary")
    val expectedSalary: String?,
    @Json(name = "cv_link")
    val cvLink: String?,
)