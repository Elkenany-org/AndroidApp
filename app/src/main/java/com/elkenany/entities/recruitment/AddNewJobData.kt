package com.elkenany.entities.recruitment

import com.squareup.moshi.Json

data class AddNewJobData(
    @Json(name = "job_detials")
    val jobDetials: JobDetials?,
    @Json(name = "company_detials")
    val companyDetials: CompanyDetials?,
)

data class JobDetials(
    val id: Long?,
    val title: String?,
    val desc: String?,
    val salary: String?,
    val phone: String?,
    val email: String?,
    val address: String?,
    val experience: String?,
    @Json(name = "category_id")
    val categoryId: String?,
    val approved: String?,
    @Json(name = "work_hours")
    val workHours: String?,
)

data class CompanyDetials(
    val id: Long?,
    val name: String?,
)