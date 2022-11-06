package com.elkenany.api.recruitment

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface IRecruitment {
    @GET("recruitment/jobs-store")
    fun getAllJobsData(
        @Header("android") isAndroid: Boolean?,
        @Header("Authorization") apiToken: String?,
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
        @Part("education") education: RequestBody?,
        @Part("experience") experience: RequestBody?,
        @Part("job_id") jobId: RequestBody?,
        @Part("expected_salary") expectedSalary: RequestBody?,
        @Part("other_info") otherInfo: RequestBody?,
        @Part cvLink: MultipartBody.Part,
        @Part("full_name") fullName: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("notice_period") noticePeriod: RequestBody?,
    ): Call<GenericEntity<ApplyData?>>

    @FormUrlEncoded
    @POST("recruitment/add-job")
    fun addNewJob(
        @Header("Authorization") apiToken: String?,
        @Field("title") jobTitle: String?,
        @Field("desc") jobDescription: String?,
        @Field("salary") jobSalary: String?,
        @Field("phone") jobPhone: String?,
        @Field("email") jobEmail: String?,
        @Field("address") jobAdress: String?,
        @Field("experience") requiredExperience: String?,
        @Field("category_id") categoryId: Long?,
        @Field("company_id") companyId: Long?,
        @Field("work_hours") workHours: String?,
    ): Call<GenericEntity<AddNewJobData?>>
}

object IRecruitmentHandler {
    val singleton: IRecruitment by lazy {
        retrofit.create(IRecruitment::class.java)
    }
}