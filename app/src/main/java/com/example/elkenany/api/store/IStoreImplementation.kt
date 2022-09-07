package com.example.elkenany.api.store

import android.util.Log
import com.example.elkenany.entities.store.*
import retrofit2.await
import java.io.File

class IStoreImplementation {

    suspend fun getAllAdsStoreData(type: String?, search: String?, header: String?): AdsStoreData? {
        return try {
            val response = IStoreHandler.singleton.getAdsStoreData(type, search, header).await()
            response.data
        } catch (e: Throwable) {
            Log.i("ads throwable", e.message.toString())
            null
        }
    }

    suspend fun getAdDetailsData(id: Long): AdsDetailsData? {
        return try {
            val response = IStoreHandler.singleton.getAdDetailsData(id).await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }

    suspend fun getAllChatsData(apiToken: String?): ChatsData? {
        return try {
            val response = IStoreHandler.singleton.getAllChatsData(apiToken).await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }

    suspend fun getAllMessagesData(id: Long, apiToken: String?): MessagesData? {
        return try {
            val response = IStoreHandler.singleton.getAllMessagesList(id, apiToken).await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }

    suspend fun getAllStartChatData(apiToken: String?, adsId: Long?): StartChatDaum? {
        return try {
            val response = IStoreHandler.singleton.getAllStartChatData(apiToken, adsId).await()
            response.data!!.chat
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }

    suspend fun sendMessageData(apiToken: String?, adsId: Long?, message: String?): MessagesList? {
        return try {
            val response = IStoreHandler.singleton.sendMessageData(apiToken, adsId, message).await()
            response.data!!.chat
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }

    suspend fun createNewAd(
        apiToken: String?,
        title: String?,
        description: String?,
        phone: String?,
        price: String?,
        sectorId: Long?,
        address: String?,
        connection: String?,
        imageFile: Array<File?>,
    ): NewAdData? {
        return try {
            val response = IStoreHandler.singleton.createNewAd(apiToken,
                title,
                description,
                phone,
                price,
                sectorId,
                address,
                connection,
                imageFile).await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }
}