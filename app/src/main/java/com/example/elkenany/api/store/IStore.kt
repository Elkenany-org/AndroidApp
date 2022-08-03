package com.example.elkenany.api.store

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.store.AdsDetailsData
import com.example.elkenany.entities.store.AdsStoreData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IStore {
    @GET("store/ads-store")
    fun getAdsStoreData(
        @Query("type") type: String?,
        @Query("search") search: String?,
    ): Call<GenericEntity<AdsStoreData?>>

    @GET("store/ads-store-detials")
    fun getAdDetailsData(@Query("id") id: Long): Call<GenericEntity<AdsDetailsData?>>
}

object IStoreHandler {
    val singleton: IStore by lazy {
        retrofit.create(IStore::class.java)
    }
}