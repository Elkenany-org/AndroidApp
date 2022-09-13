package com.example.elkenany.api.store

import android.util.Log
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.store.*
import retrofit2.HttpException
import retrofit2.await

class IStoreImplementation {

    suspend fun getAllAdsStoreData(type: String?, search: String?, header: String?): AdsStoreData? {
        return try {
            val response = IStoreHandler.singleton.getAdsStoreData(type, search, header).await()
            response.data
        } catch (e: Throwable) {
            Log.i("getAllAdsStoreData", e.message.toString())
            null
        }
    }

    suspend fun getAdDetailsData(id: Long): AdsDetailsData? {
        return try {
            val response = IStoreHandler.singleton.getAdDetailsData("android", id).await()
            response.data
        } catch (e: Throwable) {
            Log.i("getAdDetailsData", e.message.toString())
            null
        }
    }

    suspend fun getAllChatsData(apiToken: String?): ChatsData? {
        return try {
            val response = IStoreHandler.singleton.getAllChatsData(apiToken).await()
            response.data
        } catch (e: Throwable) {
            Log.i("getAllChatsData", e.message.toString())
            null
        }
    }

    suspend fun getAllMessagesData(id: Long, apiToken: String?): MessagesData? {
        return try {
            val response = IStoreHandler.singleton.getAllMessagesList(id, apiToken).await()
            response.data
        } catch (e: Throwable) {
            Log.i("getAllMessagesData", e.message.toString())
            null
        }
    }

    suspend fun getAllStartChatData(apiToken: String?, adsId: Long?): StartChatDaum? {
        return try {
            val response = IStoreHandler.singleton.getAllStartChatData(apiToken, adsId).await()
            response.data!!.chat
        } catch (e: Throwable) {
            Log.i("getAllStartChatData", e.message.toString())
            null
        }
    }

    suspend fun sendMessageData(apiToken: String?, adsId: Long?, message: String?): MessagesList? {
        return try {
            val response = IStoreHandler.singleton.sendMessageData(apiToken, adsId, message).await()
            response.data!!.chat
        } catch (e: Throwable) {
            Log.i("sendMessageData", e.message.toString())
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
        imageFile: String?,
    ): GenericEntity<NewAdData?>? {
        return try {
            val response = IStoreHandler.singleton.createNewAd(
                "android",
                apiToken,
                title,
                description,
                phone,
                price,
                sectorId,
                address,
                connection,
                imageFile
            ).await()
            response
        } catch (e: HttpException) {

            GenericEntity(null, e.code().toString(), null)
        }
    }

    suspend fun getAllMyAdsListData(
        apiToken: String?,
        sectorType: String?,
    ): GenericEntity<MyAdsListData?>? {
        return try {
            val response = IStoreHandler.singleton.getAllMyAdsListData(apiToken, sectorType).await()
            response
        } catch (e: Throwable) {
            Log.i("getAllMyAdsListData", e.message.toString())
            null
        }
    }

    suspend fun deleteAdFromDataBase(apiToken: String?, adId: Long?): Boolean? {
        return try {
            val response = IStoreHandler.singleton.deleteAdFromDataBase(apiToken, adId).await()
            response.message != null
        } catch (e: Throwable) {
            Log.i("deleteAdFromDataBase", e.message.toString())
            null
        }
    }

}