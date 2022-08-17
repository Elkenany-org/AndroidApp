package com.example.elkenany.api.guide

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.guide.CompaniesData
import com.example.elkenany.entities.guide.CompanyDetailsData
import com.example.elkenany.entities.guide.GuideData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface IGuide {
    @GET("guide/section")
    fun getAllGuideData(
        @Query("type") sectorType: String?,
        @Query("search") search: String?,
    ): Call<GenericEntity<GuideData?>>

    @GET("guide/sub-section")
    fun getAllCompaniesData(
        @Query("sub_id") subId: Long,
        @Query("search") search: String?,
        @Header("android") header: String?,
    ): Call<GenericEntity<CompaniesData?>>

    @GET("guide/company")
    fun getCompanyData(
        @Query("id") Id: Long,
        @Header("Authorization") apiToken: String?,
    ): Call<GenericEntity<CompanyDetailsData?>>
}

object IGuideHandler {
    val singleton: IGuide by lazy {
        retrofit.create(IGuide::class.java)
    }
}