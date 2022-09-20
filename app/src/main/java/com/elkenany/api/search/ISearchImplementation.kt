package com.elkenany.api.search


import android.util.Log
import com.elkenany.entities.search.SearchResult
import retrofit2.HttpException
import retrofit2.await
import java.net.SocketTimeoutException

class ISearchImplementation {

    suspend fun getAllSearchData(search: String?): SearchResult? {
        return try {
            val response = ISearchHandler.singleton.getAllSearchData(search).await()
            response.data
        } catch (e: HttpException) {
            Log.i("getAllSearchData", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getAllSearchData", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("getAllSearchData", e.message.toString())
            null
        }
    }
}