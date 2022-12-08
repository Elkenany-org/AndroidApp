package com.elkenany.api.guide_magazine

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.guide_magazine.MagazineData
import com.elkenany.entities.guide_magazine.MagazineDetailsData
import retrofit2.Call
import retrofit2.http.*

interface IMagazine {
    @GET("magazine/magazines")
    fun getAllMagazineListData(
        @Header("android") android: Boolean,
        @Query("section_id") sectionsId: String?,
        @Query("sort") sort: Long?,
        @Query("city_id") cityId: Long?,
        @Query("search") search: String?,
    ): Call<GenericEntity<MagazineData>>


    @GET("magazine/magazine-detials")
    fun getMagazineItemData(
        @Query("id") magazineId: Long?,
    ): Call<GenericEntity<MagazineDetailsData>>

    @FormUrlEncoded
    @POST("magazine/rating-magazine")
    fun rateThisMagazine(
        @Header("Authorization") apiToken: String?,
        @Field("maga_id") magazineId: Long?,
        @Field("reat") rate: Long?,
    ): Call<GenericEntity<Any?>>
}

object IMagazineHandler {
    val singleton: IMagazine by lazy {
        retrofit.create(IMagazine::class.java)
    }
}