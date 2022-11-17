package com.elkenany.api.shows

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.shows_data.ShowReviewersData
import com.elkenany.entities.shows_data.ShowsDetailsData
import com.elkenany.entities.shows_data.ShowsListData
import retrofit2.Call
import retrofit2.http.*

interface IShows {

    @GET("showes/all-showes")
    fun getAllShowsData(
        @Header("android") android: Boolean?,
        @Query("section_id") sectorType: String?,
        @Query("search") search: String?,
        @Query("sort") sort: Long?,
        @Query("city_id") cityId: Long?,
        @Query("country_id") countryId: Long?,
    ): Call<GenericEntity<ShowsListData>>

    @GET("showes/one-show/")
    fun getShowsDetailsData(@Query("id") id: Long?): Call<GenericEntity<ShowsDetailsData>>

    @FormUrlEncoded
    @POST("showes/one-show-going")
    fun goingState(
        @Header("Authorization") apiToken: String?,
        @Field("show_id") showId: Long?,
    ): Call<GenericEntity<Any>>

    @FormUrlEncoded
    @POST("showes/one-show-notgoing")
    fun notGoingState(
        @Header("Authorization") apiToken: String?,
        @Field("show_id") showId: Long?,
    ): Call<GenericEntity<Any>>

    @GET("showes/one-show-review/")
    fun getAllShowReviewersData(@Query("id") showId: Long?): Call<GenericEntity<ShowReviewersData>>
}

object IShowsHandler {
    val singleton: IShows by lazy {
        retrofit.create(IShows::class.java)
    }
}