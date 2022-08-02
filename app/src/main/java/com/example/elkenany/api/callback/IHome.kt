package com.example.elkenany.api.callback

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.home_data.HomeSectorsData
import com.example.elkenany.entities.home_data.HomeServiceData
import com.example.elkenany.entities.home_data.NotificationsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface IHome {

    // ToDo --> implement functions that are required for receiving data from backend

    //Retrofit homeSectors data callback
    @GET("home-sectors")
    fun getSectorsData(): Call<GenericEntity<HomeSectorsData?>>

    //Retrofit homeServices data callback
    @GET("home-services")
    fun getServicesData(): Call<GenericEntity<HomeServiceData?>>

    @GET("notfications")
    fun getAllNotificationData(@Header("Authorization") apiToken: String): Call<GenericEntity<NotificationsData?>>
}

object IHomeHandler {
    val singleton: IHome by lazy {
        retrofit.create(IHome::class.java)
    }
}