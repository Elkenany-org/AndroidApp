package com.elkenany.api.shows

import android.util.Log
import com.elkenany.entities.shows_data.ShowsDetailsData
import com.elkenany.entities.shows_data.ShowsListData
import retrofit2.HttpException
import retrofit2.await
import java.net.SocketTimeoutException

class IShowsImplementation {
    suspend fun getAllShowsListData(
        sectorType: String?,
        search: String?,
        sort: Long?,
        cityId: Long?,
        countryId: Long?,
    ): ShowsListData? {
        return try {
            val response =
                IShowsHandler.singleton.getAllShowsData(true,
                    sectorType,
                    search,
                    sort,
                    cityId,
                    countryId)
                    .await()
            response.data
        } catch (e: HttpException) {
            Log.i("getAllShowsListData", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getAllShowsListData", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("getAllShowsListData", e.message.toString())
            null
        }
    }

    suspend fun getShowsDetailsData(
        showId: Long?,
    ): ShowsDetailsData? {
        return try {
            val response =
                IShowsHandler.singleton.getShowsDetailsData(showId)
                    .await()
            response.data
        } catch (e: HttpException) {
            Log.i("getShowsDetailsData", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getShowsDetailsData", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("getShowsDetailsData", e.message.toString())
            null
        }
    }
}