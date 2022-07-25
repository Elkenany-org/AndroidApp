package com.example.elkenany.api.guide

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.guide.GuideData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IGuide {
    @GET("guide/section")
    fun getAllGuideData(
        @Query("type") sectorType: String?,
        @Query("search") search: String?,
    ): Call<GenericEntity<GuideData?>>
}

object IGuideHandler {
    val singleton: IGuide by lazy {
        retrofit.create(IGuide::class.java)
    }
}