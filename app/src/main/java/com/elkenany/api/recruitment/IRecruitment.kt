package com.elkenany.api.recruitment

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.AddToFavoriteData
import com.elkenany.entities.recruitment.JobDetailsData
import com.elkenany.entities.recruitment.JobsData
import com.elkenany.entities.recruitment.MyJobsData
import retrofit2.Call
import retrofit2.http.*

interface IRecruitment {
    @GET("recruitment/jobs-store")
    fun getAllJobsData(
        @Header("android") isAndroid: Boolean?,
        @Query("sort") sort: Int?,
        @Query("cate") category: String?,
        @Query("search") search: String?,
    ): Call<GenericEntity<JobsData?>>

    @GET("recruitment/job-detials")
    fun getJobDetailsData(@Query("id") jobId: Int?): Call<GenericEntity<JobDetailsData?>>

    @GET("recruitment/my-jobs-store")
    fun getMyJobsList(
        @Header("android") isAndroid: Boolean?,
        @Header("Authorization") apiToken: String?,
    ): Call<GenericEntity<MyJobsData?>>

    @FormUrlEncoded
    @POST("recruitment/add-to-job-favorites")
    fun addJobToMyFavorites(
        @Header("Authorization") apiToken: String?,
        @Field("job_id") jobId: Int?,
    ): Call<GenericEntity<AddToFavoriteData?>>
}

object IRecruitmentHandler {
    val singleton: IRecruitment by lazy {
        retrofit.create(IRecruitment::class.java)
    }
}