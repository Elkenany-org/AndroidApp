package com.elkenany.api.shows

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.shows_data.ShowsDetailsData
import com.elkenany.entities.shows_data.ShowsListData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface IShows {

    @GET("showes/all-showes")
    fun getAllShowsData(
        @Header("android") android: Boolean?,
        @Query("type") sectorType: String?,
        @Query("search") search: String?,
        @Query("sort") sort: Long?,
        @Query("city_id") cityId: Long?,
        @Query("country_id") countryId: Long?,
    ): Call<GenericEntity<ShowsListData>>

    @GET("showes/one-show/")
    fun getShowsDetailsData(@Query("id") id: Long?): Call<GenericEntity<ShowsDetailsData>>
}

object IShowsHandler {
    val singleton: IShows by lazy {
        retrofit.create(IShows::class.java)
    }
}