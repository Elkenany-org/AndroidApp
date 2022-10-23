package com.elkenany.api.recruitment

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.JobsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRecruitment {
    @GET("recruitment/jobs-store")
    fun getAllJobsData(
        @Query("sort") sort: Int?,
        @Query("cate") category: String?,
        @Query("search") search: String?,
    ): Call<GenericEntity<JobsData?>>
}

object IRecruitmentHandler {
    val singleton: IRecruitment by lazy {
        retrofit.create(IRecruitment::class.java)
    }
}