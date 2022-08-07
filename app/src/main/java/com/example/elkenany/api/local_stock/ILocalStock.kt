package com.example.elkenany.api.local_stock

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.stock_data.LocalStockData
import com.example.elkenany.entities.stock_data.LocalStockDetailsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ILocalStock {
    @GET("localstock/local-stock-sections")
    fun getLocalStockSectionsData(@Query("type") type: String): Call<GenericEntity<LocalStockData?>>

    @GET("v2/local-android/tables")
    fun getLocalStockDetailsByIdAndType(
        @Query("id") id: Long,
        @Query("date") date: String?,
    ): Call<GenericEntity<LocalStockDetailsData?>>
}

object ILocalStockHandler {
    val singleton: ILocalStock by lazy {
        retrofit.create(ILocalStock::class.java)
    }
}