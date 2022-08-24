package com.example.elkenany.api.local_stock

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.stock_data.LocalStockData
import com.example.elkenany.entities.stock_data.LocalStockDetailsData
import com.example.elkenany.entities.stock_data.StatisticsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ILocalStock {
    @GET("localstock/local-stock-sections")
    fun getLocalStockSectionsData(
        @Query("type") type: String,
        @Query("search") search: String?,
    ): Call<GenericEntity<LocalStockData?>>

    @GET("v2/local-android/tables")
    fun getLocalStockDetailsByIdAndTypeLocal(
        @Query("id") id: Long,
        @Query("date") date: String?,
    ): Call<GenericEntity<LocalStockDetailsData?>>

    @GET("v2/fodder-android/tables")
    fun getLocalStockDetailsByIdAndTypeFodder(
        @Query("id") id: Long,
        @Query("date") date: String?,
    ): Call<GenericEntity<LocalStockDetailsData?>>

    @GET("localstock/feeds-items")
    fun getLocalStockFeedItems(
        @Query("stock_id") stockId: String?,
        @Query("mini_id") miniId: String?,
        @Query("food_Id") foodId: String?,
        @Query("device") deviceType: String?,
    ): Call<GenericEntity<Any?>>

    @GET("localstock/companies-items")
    fun getLocalStockCompanyItems(
        @Query("stock_id") stockId: String?,
        @Query("company_id") companyId: String?,
    ): Call<GenericEntity<Any?>>

    @GET("localstock/statistics-Localstock-members")
    fun getAllStatisticsData(
        @Query("id") stockId: Long?,
        @Query("type") type: String?,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Header("Authorization") authorization: String?,
    ): Call<GenericEntity<StatisticsData?>>
}

object ILocalStockHandler {
    val singleton: ILocalStock by lazy {
        retrofit.create(ILocalStock::class.java)
    }
}