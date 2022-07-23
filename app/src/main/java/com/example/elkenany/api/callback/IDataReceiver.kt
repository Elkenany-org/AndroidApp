package com.example.elkenany.api.callback

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.home_data.HomeSectorsData
import com.example.elkenany.entities.home_data.HomeServiceData
import com.example.elkenany.entities.stock_data.LocalStockData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataReceiver {

    // ToDo --> implement functions that are required for receiving data from backend

    //Retrofit homeSectors data callback
    @GET("home-sectors")
    fun getSectorsData(): Call<GenericEntity<HomeSectorsData?>>

    //Retrofit homeServices data callback
    @GET("home-services")
    fun getServicesData(): Call<GenericEntity<HomeServiceData?>>

    @GET("localstock/local-stock-sections")
    fun getLocalStockSectionsData(@Query("type") type: String): Call<GenericEntity<LocalStockData?>>
}

object DataReceiverHandler {
    val singleton: IDataReceiver by lazy {
        retrofit.create(IDataReceiver::class.java)
    }
}