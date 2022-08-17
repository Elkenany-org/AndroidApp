package com.example.elkenany.api.local_stock

import android.util.Log
import com.example.elkenany.entities.stock_data.LocalStockData
import com.example.elkenany.entities.stock_data.LocalStockDetailsData
import retrofit2.await

class ILocalStockImplementation {

    suspend fun getLocalStockSectionsData(sectorType: String): LocalStockData? {
        return try {
            val response =
                ILocalStockHandler.singleton.getLocalStockSectionsData(sectorType).await()
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }

    suspend fun getLocalStockDetailsByIdAndType(
        id: Long,
        date: String?,
        type: String,
    ): LocalStockDetailsData? {
        Log.i("sectionType", type)
        return try {
            val response = if (type == "local") {
                ILocalStockHandler.singleton.getLocalStockDetailsByIdAndTypeLocal(id, date).await()
            } else {
                ILocalStockHandler.singleton.getLocalStockDetailsByIdAndTypeFodder(id, date).await()
            }
            response.data
        } catch (e: Throwable) {
            Log.i("throwable", e.message.toString())
            null
        }
    }
}