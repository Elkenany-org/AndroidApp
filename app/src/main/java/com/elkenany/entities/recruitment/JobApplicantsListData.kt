package com.elkenany.entities.recruitment

import com.squareup.moshi.Json

data class JobApplicantsListData(
    @Json(name = "job_title")
    val jobTitle: String?,
    val applicants: List<Applicant>?,
)

data class Applicant(
    val id: Long?,
    val name: String?,
    val email: String?,
    @Json(name ="created_at")
    val createdAt: String?,
    val cv: String?,
    val image: String?,
)