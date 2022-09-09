package com.example.elkenany.api.news

import android.util.Log
import com.example.elkenany.entities.news_data.NewsData
import com.example.elkenany.entities.news_data.NewsDetailsData
import retrofit2.await

class INewsImplementation {
    suspend fun getAllNewsData(type: String?, search: String?,sort:String?): NewsData? {
        return try {
            val response = INewsHandler.singleton.getAllNews(type, search,sort).await()
            response.data
        } catch (e: Throwable) {
            Log.i("INewsImplementation", e.message.toString())
            null
        }
    }

    suspend fun getNewsDetailsData(id: Int): NewsDetailsData? {
        return try {
            val response = INewsHandler.singleton.getNewsDetailsByID(id).await()
            response.data
        } catch (e: Throwable) {
            Log.i("getNewsDetailsData", e.message.toString())
            null
        }
    }
}