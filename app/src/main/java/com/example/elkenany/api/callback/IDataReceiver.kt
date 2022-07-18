package com.example.elkenany.api.callback

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.home_data.HomeSectorsData
import retrofit2.Call
import retrofit2.http.GET

interface IDataReceiver {

    // ToDo --> implement functions that are required for receiving data from backend

    @GET("home-sectors")
    fun getSectorsData(): Call<GenericEntity<HomeSectorsData?>>
}

object DataReceiverHandler {
    val singleton: IDataReceiver by lazy {
        retrofit.create(IDataReceiver::class.java)
    }
}