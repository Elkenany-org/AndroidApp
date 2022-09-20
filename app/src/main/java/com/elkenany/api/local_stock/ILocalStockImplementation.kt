package com.elkenany.api.local_stock

import android.util.Log
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.stock_data.*
import retrofit2.HttpException
import retrofit2.await
import java.net.SocketTimeoutException

class ILocalStockImplementation {

    suspend fun getLocalStockSectionsData(sectorType: String, search: String?): LocalStockData? {
        return try {
            val response =
                ILocalStockHandler.singleton.getLocalStockSectionsData(sectorType, search).await()
            response.data
        } catch (e: HttpException) {
            Log.i("getLocalStockSectionsData", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getLocalStockSectionsData", e.message.toString())
            null
        }catch (e: Exception) {
            Log.i("getLocalStockSectionsData", e.message.toString())
            null
        }
    }

    suspend fun getLocalStockDetailsByIdAndType(
        id: Long,
        date: String?,
        type: String,
        feedId: String?,
        companyId: String?,
    ): LocalStockDetailsData? {
        Log.i("sectionType", type)
        return try {
            val response = if (type == "local") {
                ILocalStockHandler.singleton.getLocalStockDetailsByIdAndTypeLocal(id, date).await()
            } else {
                ILocalStockHandler.singleton.getLocalStockDetailsByIdAndTypeFodder(
                    "device",
                    id,
                    date,
                    feedId,
                    companyId
                ).await()
            }
            response.data
        } catch (e: HttpException) {
            Log.i("getLocalStockDetailsByIdAndType", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getLocalStockDetailsByIdAndType", e.message.toString())
            null
        }catch (e: Exception) {
            Log.i("getLocalStockDetailsByIdAndType", e.message.toString())
            null
        }
    }

    suspend fun getLocalStockFeedsItems(stockId: Long?): FeedsData? {
        return try {
            val response =
                ILocalStockHandler.singleton.getLocalStockFeedItems("web", stockId).await()
            Log.i("getLocalStockCompanyItems", response.data.toString())
            response.data
        } catch (e: HttpException) {
            Log.i("getLocalStockFeedsItems", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getLocalStockFeedsItems", e.message.toString())
            null
        }catch (e: Exception) {
            Log.i("getGuideFilterData", e.message.toString())
            null
        }
    }

    suspend fun getLocalStockCompanyItems(stockId: Long?): List<LocalStockCompanyDaum?>? {
        return try {
            val response =
                ILocalStockHandler.singleton.getLocalStockCompanyItems(stockId).await()
            response.data
        } catch (e: HttpException) {
            Log.i("getLocalStockCompanyItems", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getLocalStockCompanyItems", e.message.toString())
            null
        }catch (e: Exception) {
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
    ): GenericEntity<StatisticsLocalData?> {
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
            response
        } catch (e: HttpException) {
            if (e.code() == 402) {
                GenericEntity(null, e.code().toString(), null)
            } else {
                GenericEntity(null, null, null)
            }
        } catch (e: SocketTimeoutException) {
            Log.i("getAllStatisticsLocalData", e.message.toString())
            GenericEntity(null, "socket time out", null)
        }catch (e: SocketTimeoutException) {
            Log.i("getAllStatisticsLocalData", e.message.toString())
            GenericEntity(null, "socket time out", null)
        } catch (e: Exception) {
            Log.i("getAllStatisticsLocalData", e.message.toString())
            GenericEntity(null, "Exception : ${e.message}", null)
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
                GenericEntity(null, "400", null)
            }
        } catch (e: SocketTimeoutException) {
            Log.i("getAllStatisticsFodderData", e.message.toString())
            GenericEntity(null, "socket time out", null)
        } catch (e: Exception) {
            Log.i("getAllStatisticsFodderData", e.message.toString())
            GenericEntity(null, "Exception ${e.message}", null)
        }
    }
}