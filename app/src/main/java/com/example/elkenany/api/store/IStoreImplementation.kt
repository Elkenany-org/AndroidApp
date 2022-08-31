package com.example.elkenany.api.store

import android.util.Log
import com.example.elkenany.entities.store.*
import retrofit2.await

class IStoreImplementation {

    suspend fun getAllAdsStoreData(type: String?, search: String?): AdsStoreData? {
        return try {
            val response = IStoreHandler.singleton.getAdsStoreData(type, search).await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
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
}