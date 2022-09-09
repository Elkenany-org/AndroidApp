package com.example.elkenany.api.callback

import android.util.Log
import com.example.elkenany.entities.home_data.*
import retrofit2.await

class IHomeImplementation {
    // ToDo --> implement all the required function to receive data from the backend
    //getting home sectors data from database through api calling
    suspend fun getHomeSectorsData(): HomeSectorsData? {
        return try {
            val response = IHomeHandler.singleton.getSectorsData().await()
            response.data
        } catch (e: Throwable) {
            Log.i("getHomeSectorsData", e.message.toString())
            null
        }
    }

    suspend fun getHomeServiceData(): HomeServiceData? {
        return try {
            val response = IHomeHandler.singleton.getServicesData().await()
            response.data
        } catch (e: Throwable) {
            Log.i("getHomeServiceData", e.message.toString())
            null
        }
    }

    suspend fun getAllNotificationData(apiToken: String): NotificationsData? {
        return try {
            val response = IHomeHandler.singleton.getAllNotificationData(apiToken).await()
            response.data
        } catch (e: Throwable) {
            Log.i("getAllNotificationData", e.message.toString())
            null
        }
    }

    suspend fun getAllContactUsData(): ContactUsData? {
        return try {
            val response = IHomeHandler.singleton.getContactUsData().await()
            response.data
        } catch (e: Throwable) {
            Log.i("getAllContactUsData", e.message.toString())
            null
        }
    }

//    suspend fun getAboutUsData(): AboutUsData? {
//        return try {
//            val response = IHomeHandler.singleton.getAboutUsData().await()
//            response.data
//        } catch (e: Throwable) {
//            Log.i("getAboutUsData", e.message.toString())
//            null
//        }
//    }
}
