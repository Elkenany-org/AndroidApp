package com.elkenany.api.guide_magazine

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.guide_magazine.MagazineData
import com.elkenany.entities.guide_magazine.MagazineDetailsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface IMagazine {
    @GET("magazine/magazines")
    fun getAllMagazineListData(
        @Header("android") android: Boolean,
        @Query("type") sectorType: String?,
        @Query("sort") sort: Long?,
        @Query("city_id") cityId: Long?,
        @Query("search") search: String?,
    ): Call<GenericEntity<MagazineData>>


    @GET("magazine/magazine-detials")
    fun getMagazineItemData(
        @Query("id") magazineId: Long?,
    ): Call<GenericEntity<MagazineDetailsData>>
}

object IMagazineHandler {
    val singleton: IMagazine by lazy {
        retrofit.create(IMagazine::class.java)
    }
}