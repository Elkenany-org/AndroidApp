package com.elkenany.api.tenders

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.tenders.TendersDetailsData
import com.elkenany.entities.tenders.TendersListData
import com.elkenany.entities.tenders.TendersSectionsListData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ITenders {

    @GET("tenders/tenders-sections")
    fun getAllTendersSectionsData(
        @Header("android") isAndroid: Boolean?,
        @Query("search") search: String?,
    ): Call<GenericEntity<TendersSectionsListData?>>


    @GET("tenders/tenders")
    fun getAllTendersListData(
        @Query("section_id") sectionId: Long?,
        @Query("sort") sort: Long?,
        @Query("search") search: String?,
    ): Call<GenericEntity<TendersListData?>>


    @GET("tenders/tenders-detials")
    fun getTendersDetailsData(
        @Query("id") id: Long?,
    ): Call<GenericEntity<TendersDetailsData?>>
}

object ITendersHandler {
    val singleton: ITenders by lazy {
        retrofit.create(ITenders::class.java)
    }
}
