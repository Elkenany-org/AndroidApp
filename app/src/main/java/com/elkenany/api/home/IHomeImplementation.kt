package com.elkenany.api.home

import android.util.Log
import com.elkenany.api.retrofit_configs.onHandelingResponseStates
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.home_data.*
import retrofit2.HttpException
import retrofit2.await
import java.net.SocketTimeoutException

class IHomeImplementation {
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

    suspend fun getHomeServiceData(apiToken: String?): HomeServiceData? {
        return try {
            val response = IHomeHandler.singleton.getServicesData(true, apiToken).await()
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

    suspend fun getPopUpAdData(): GenericEntity<PopUpData?> {
        return onHandelingResponseStates("getPopUpAdData") {
            IHomeHandler.singleton.getPopUpData()
        }
    }

    suspend fun getAllSponsersData(): GenericEntity<SponsersListData?> {
        return onHandelingResponseStates("getAllSponsersData") {
            IHomeHandler.singleton.getAllSponsersData()
        }
    }
}
