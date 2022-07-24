package com.example.elkenany.api.store

import android.util.Log
import com.example.elkenany.entities.store.AdsStoreData
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
}