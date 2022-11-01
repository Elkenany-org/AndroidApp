package com.elkenany.api.recruitment

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.*
import okhttp3.MultipartBody
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

    @GET("recruitment/job-favorites")
    fun getMyFavoriteJobsListData(
        @Header("android") isAndroid: Boolean?,
        @Header("Authorization") apiToken: String?,
    ): Call<GenericEntity<MyFavoriteJobsListData?>>


    @FormUrlEncoded
    @POST("recruitment/add-to-job-favorites")
    fun addJobToMyFavorites(
        @Header("Authorization") apiToken: String?,
        @Field("job_id") jobId: Int?,
    ): Call<GenericEntity<AddToFavoriteData?>>

    @Multipart
    @POST("recruitment/apply-job")
    fun applyToJob(
        @Header("Authorization") apiToken: String?,
        @Part("education") education: String?,
        @Part("experience") experience: String?,
        @Part("job_id") jobId: Int?,
        @Part("expected_salary") expectedSalary: String?,
        @Part("other_info") otherInfo: String?,
        @Part("cv_link") cv_file: MultipartBody.Part,
        @Part("full_name") fullName: String?,
        @Part("phone") phone: String?,
        @Part("notice_period") noticePeriod: String?,
    ): Call<GenericEntity<ApplyData?>>
}

object IRecruitmentHandler {
    val singleton: IRecruitment by lazy {
        retrofit.create(IRecruitment::class.java)
    }
}