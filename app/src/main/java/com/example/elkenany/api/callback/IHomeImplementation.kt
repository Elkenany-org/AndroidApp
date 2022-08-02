package com.example.elkenany.api.callback

import android.util.Log
import com.example.elkenany.entities.home_data.HomeSectorsData
import com.example.elkenany.entities.home_data.HomeServiceData
import com.example.elkenany.entities.home_data.NotificationsData
import retrofit2.await

class IHomeImplementation {
    // ToDo --> implement all the required function to receive data from the backend
    //getting home sectors data from database through api calling
    suspend fun getHomeSectorsData(): HomeSectorsData? {
        return try {
            val response = IHomeHandler.singleton.getSectorsData().await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }

    suspend fun getHomeServiceData(): HomeServiceData? {
        return try {
            val response = IHomeHandler.singleton.getServicesData().await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }

    suspend fun getAllNotificationData(apiToken: String): NotificationsData? {
        return try {
            val response = IHomeHandler.singleton.getAllNotificationData(apiToken).await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }

}
