package com.example.elkenany.api.news

import android.util.Log
import com.example.elkenany.entities.news_data.NewsData
import retrofit2.await

class INewsImplementation {
    suspend fun getAllNewsData(type: String?, search: String?): NewsData? {
        return try {
            val response = INewsHandler.singleton.getAllNews(type, search).await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }
}