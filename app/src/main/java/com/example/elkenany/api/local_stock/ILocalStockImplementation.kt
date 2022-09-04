package com.example.elkenany.api.local_stock

import android.util.Log
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.stock_data.LocalStockData
import com.example.elkenany.entities.stock_data.LocalStockDetailsData
import com.example.elkenany.entities.stock_data.StatisticsFodderData
import com.example.elkenany.entities.stock_data.StatisticsLocalData
import retrofit2.HttpException
import retrofit2.await
import java.net.SocketTimeoutException

class ILocalStockImplementation {

    suspend fun getLocalStockSectionsData(sectorType: String, search: String?): LocalStockData? {
        return try {
            val response =
                ILocalStockHandler.singleton.getLocalStockSectionsData(sectorType, search).await()
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

    suspend fun getLocalStockFeedsItems(): Any {
        // ToDo -> impelement getFeedItems from backend
        throw Exception()
    }

    suspend fun getLocalStockCompanyItems(): Any {
        // ToDo -> impelement getCompanyItems from backend
        throw Exception()
    }

    suspend fun getAllStatisticsLocalData(
        stockId: Long?,
        type: String?,
        from: String?,
        to: String?,
        memId: Long?,
        authroization: String?,
    ): StatisticsLocalData? {
        return try {
            val response =
                ILocalStockHandler.singleton.getAllStatisticsLocalData(stockId,
                    type,
                    from,
                    to,
                    memId,
                    authroization).await()
            response.data
        } catch (e: HttpException) {
            Log.i("throwable", e.code().toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("throwable", e.cause.toString())
            null
        }
    }

    suspend fun getAllStatisticsFodderData(
        apiToken: String?,
        from: String?,
        to: String?,
        fodderId: Long?,
        companyId: Long?,
    ): GenericEntity<StatisticsFodderData?> {
        return try {
            val response =
                ILocalStockHandler.singleton.getAllStatisticsFodderData(apiToken,
                    from,
                    to,
                    fodderId,
                    companyId).await()
            response
        } catch (e: HttpException) {
            if (e.code() == 402) {
                GenericEntity(null, "402", null)
            } else {
                GenericEntity(null, null, null)
            }
        } catch (e: SocketTimeoutException) {
            Log.i("throwable", e.message.toString())
            GenericEntity(null, "socket time out", null)
        }
    }
}