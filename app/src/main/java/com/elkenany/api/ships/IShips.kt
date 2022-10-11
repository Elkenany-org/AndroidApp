package com.elkenany.api.ships

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.ships.ShipsListData
import com.elkenany.entities.ships.ShipsStatisticsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IShips {

    @GET("ships/all-ships")
    fun getAllShipsData(@Query("date") date: String?): Call<GenericEntity<ShipsListData>>

    @GET("ships/statistics-ships")
    fun getShipsStatisticsData(
        @Query("type") type: String?,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("country") country: String?,
    ): Call<GenericEntity<ShipsStatisticsData>>

}

object IShipsHandler {
    val singleton: IShips by lazy {
        retrofit.create(IShips::class.java)
    }
}