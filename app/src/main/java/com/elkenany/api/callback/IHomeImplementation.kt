package com.elkenany.api.callback

import android.util.Log
import com.elkenany.entities.home_data.ContactUsData
import com.elkenany.entities.home_data.HomeSectorsData
import com.elkenany.entities.home_data.HomeServiceData
import com.elkenany.entities.home_data.NotificationsData
import retrofit2.HttpException
import retrofit2.await
import java.net.SocketTimeoutException

class IHomeImplementation {
    // ToDo --> implement all the required function to receive data from the backend
    //getting home sectors data from database through api calling
    suspend fun getHomeSectorsData(): HomeSectorsData? {
        return try {
            val response = IHomeHandler.singleton.getSectorsData(true).await()
            response.data
        } catch (e: HttpException) {
            Log.i("getHomeSectorsData", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getHomeSectorsData", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("getHomeSectorsData", e.message.toString())
            null
        }
    }

    suspend fun getHomeServiceData(): HomeServiceData? {
        return try {
            val response = IHomeHandler.singleton.getServicesData(true).await()
            response.data
        } catch (e: HttpException) {
            Log.i("getHomeServiceData", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getHomeServiceData", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("getHomeServiceData", e.message.toString())
            null
        }
    }

    suspend fun getAllNotificationData(apiToken: String): NotificationsData? {
        return try {
            val response = IHomeHandler.singleton.getAllNotificationData(apiToken).await()
            response.data
        } catch (e: HttpException) {
            Log.i("getAllNotificationData", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getAllNotificationData", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("getAllNotificationData", e.message.toString())
            null
        }
    }

    suspend fun getAllContactUsData(): ContactUsData? {
        return try {
            val response = IHomeHandler.singleton.getContactUsData().await()
            response.data
        } catch (e: HttpException) {
            Log.i("getAllContactUsData", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getAllContactUsData", e.message.toString())
            null
        } catch (e: Exception) {
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
