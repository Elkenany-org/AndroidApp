package com.example.elkenany.api.guide

import android.util.Log
import com.example.elkenany.entities.guide.GuideData
import retrofit2.await

class IGuideImplementation {

    suspend fun getAllGuideData(sectorType: String?, search: String?): GuideData? {
        return try {
            val response = IGuideHandler.singleton.getAllGuideData(sectorType, search).await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null

        }
    }
}