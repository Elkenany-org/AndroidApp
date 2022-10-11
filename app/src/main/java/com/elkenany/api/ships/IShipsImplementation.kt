package com.elkenany.api.ships

import android.util.Log
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.ships.ShipsListData
import com.elkenany.entities.ships.ShipsStatisticsData
import retrofit2.HttpException
import retrofit2.await
import java.net.SocketTimeoutException

class IShipsImplementation {

    suspend fun getAllShipsData(
        date: String?,
    ): GenericEntity<ShipsListData> {
        return try {
            val response = IShipsHandler.singleton.getAllShipsData(date)
                .await()
            response
        } catch (e: HttpException) {
            Log.i("getAllShipsData", e.code().toString())
            GenericEntity(message = null, error = e.code().toString(), null)
        } catch (e: SocketTimeoutException) {
            Log.i("getAllShipsData", e.message.toString())
            GenericEntity(message = null, error = "500", null)
        } catch (e: Exception) {
            Log.i("getAllShipsData", e.message.toString())
            GenericEntity(message = null, error = "500", null)
        }
    }

    suspend fun getShipsStatisticsData(
        type: String?,
        from: String?,
        to: String?,
        country: String?,
    ): ShipsStatisticsData? {
        return try {
            val response = IShipsHandler.singleton.getShipsStatisticsData(type, from, to, country)
                .await()
            response.data
        } catch (e: HttpException) {
            Log.i("getAllShipsData", e.code().toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getAllShipsData", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("getAllShipsData", e.message.toString())
            null
        }
    }
}