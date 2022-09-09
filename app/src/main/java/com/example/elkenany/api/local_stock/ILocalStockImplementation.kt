package com.example.elkenany.api.local_stock

import android.util.Log
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.stock_data.*
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
            Log.i("getLocalStockSectionsData", e.message.toString())
            null
        }
    }

    suspend fun getLocalStockDetailsByIdAndType(
        id: Long,
        date: String?,
        type: String,
        feedId: String?,
        companyId: String?
    ): LocalStockDetailsData? {
        Log.i("sectionType", type)
        return try {
            val response = if (type == "local") {
                ILocalStockHandler.singleton.getLocalStockDetailsByIdAndTypeLocal(id, date).await()
            } else {
                ILocalStockHandler.singleton.getLocalStockDetailsByIdAndTypeFodder(
                    id,
                    date,
                    feedId,
                    companyId
                ).await()
            }
            response.data
        } catch (e: Throwable) {
            Log.i("getLocalStockDetailsByIdAndType", e.message.toString())
            null
        }
    }

    suspend fun getLocalStockFeedsItems(stockId: Long?): FeedsData? {
        return try {
            val response =
                ILocalStockHandler.singleton.getLocalStockFeedItems(stockId).await()
            response.data
        } catch (e: Throwable) {
            Log.i("getLocalStockFeedsItems", e.message.toString())
            null
        }
    }

    suspend fun getLocalStockCompanyItems(stockId: Long?): List<LocalStockCompanyDaum?>? {
        return try {
            val response =
                ILocalStockHandler.singleton.getLocalStockCompanyItems(stockId).await()
            response.data
        } catch (e: Throwable) {
            Log.i("getLocalStockCompanyItems", e.message.toString())
            null
        }
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
                ILocalStockHandler.singleton.getAllStatisticsLocalData(
                    stockId,
                    type,
                    from,
                    to,
                    memId,
                    authroization
                ).await()
            response.data
        } catch (e: HttpException) {
            Log.i("getAllStatisticsLocalData", e.code().toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getAllStatisticsLocalData", e.cause.toString())
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
                ILocalStockHandler.singleton.getAllStatisticsFodderData(
                    apiToken,
                    from,
                    to,
                    fodderId,
                    companyId
                ).await()
            response
        } catch (e: HttpException) {
            if (e.code() == 402) {
                GenericEntity(null, "402", null)
            } else {
                GenericEntity(null, null, null)
            }
        } catch (e: SocketTimeoutException) {
            Log.i("getAllStatisticsFodderData", e.message.toString())
            GenericEntity(null, "socket time out", null)
        }
    }
}