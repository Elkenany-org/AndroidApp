package com.example.elkenany.api.local_stock

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.stock_data.*
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
        @Query("fod_id") feedId: String?,
        @Query("comp_id") companyId: String?
    ): Call<GenericEntity<LocalStockDetailsData?>>

    @GET("localstock/feeds-items")
    fun getLocalStockFeedItems(
        @Query("stock_id") stockId: Long?,
    ): Call<GenericEntity<FeedsData?>>

    @GET("localstock/companies-items")
    fun getLocalStockCompanyItems(
        @Query("stock_id") stockId: Long?,
    ): Call<GenericEntity<List<LocalStockCompanyDaum?>?>>

    @GET("localstock/statistics-Localstock-members")
    fun getAllStatisticsLocalData(
        @Query("id") stockId: Long?,
        @Query("type") type: String?,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("mem_id") memId: Long?,
        @Header("Authorization") authorization: String?,
    ): Call<GenericEntity<StatisticsLocalData?>>

    @GET("fodderstock/statistics-Fodderstock-members")
    fun getAllStatisticsFodderData(
        @Header("Authorization") authorization: String?,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("id") fodderId: Long?,
        @Query("com_id") companyId: Long?,
    ): Call<GenericEntity<StatisticsFodderData?>>
}

object ILocalStockHandler {
    val singleton: ILocalStock by lazy {
        retrofit.create(ILocalStock::class.java)
    }
}