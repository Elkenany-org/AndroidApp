package com.elkenany.api.callback

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.home_data.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface IHome {

    // ToDo --> implement functions that are required for receiving data from backend

    //Retrofit homeSectors data callback
    @GET("home-sectors")
    fun getSectorsData(@Header("android") isAndroid: Boolean?): Call<GenericEntity<HomeSectorsData?>>

    //Retrofit homeServices data callback
    @GET("home-services")
    fun getServicesData(@Header("android") isAndroid: Boolean?): Call<GenericEntity<HomeServiceData?>>

    @GET("v2/notifications")
    fun getAllNotificationData(@Header("Authorization") apiToken: String): Call<GenericEntity<NotificationsData?>>

    @GET("about-us")
    fun getAboutUsData(): Call<GenericEntity<AboutUsData?>>

    @GET("contuct-us-office")
    fun getContactUsData(): Call<GenericEntity<ContactUsData?>>
}

object IHomeHandler {
    val singleton: IHome by lazy {
        retrofit.create(IHome::class.java)
    }
}