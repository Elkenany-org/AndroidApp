package com.example.elkenany.api.search


import android.util.Log
import com.example.elkenany.entities.search.SearchResult
import retrofit2.await

class ISearchImplementation {

    suspend fun getAllSearchData(search: String?): SearchResult? {
        return try {
            val response = ISearchHandler.singleton.getAllSearchData(search).await()
            response.data
        } catch (e: Throwable) {
            Log.i("getAllSearchData", e.message.toString())
            null
        }
    }
}