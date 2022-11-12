package com.elkenany.api.local_stock

import android.util.Log
import com.elkenany.api.retrofit_configs.onHandelingResponseStates
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.stock_data.*

class ILocalStockImplementation {

    suspend fun getLocalStockSectionsData(
        sectionId: Long?,
        search: String?
    ): GenericEntity<LocalStockData?> {
        return onHandelingResponseStates("getLocalStockSectionsData") {
            ILocalStockHandler.singleton.getLocalStockSectionsData(sectionId, search)
        }
    }

    suspend fun getLocalStockDetailsByIdAndType(
        id: Long,
        date: String?,
        type: String,
        feedId: String?,
        companyId: String?,
    ): GenericEntity<LocalStockDetailsData?> {
        Log.i("sectionType", type)
        return if (type == "local") {
            onHandelingResponseStates("getLocalStockDetailsByIdAndType") {
                ILocalStockHandler.singleton.getLocalStockDetailsByIdAndTypeLocal(
                    true,
                    id,
                    date
                )
            }
        } else {
            onHandelingResponseStates("getLocalStockDetailsByIdAndType") {
                ILocalStockHandler.singleton.getLocalStockDetailsByIdAndTypeFodder(
                    true,
                    "device",
                    id,
                    date,
                    feedId,
                    companyId
                )
            }
        }

    }

    suspend fun getLocalStockFeedsItems(stockId: Long?): GenericEntity<FeedsData?> {
        return onHandelingResponseStates("getLocalStockFeedsItems") {
            ILocalStockHandler.singleton.getLocalStockFeedItems("web", stockId)
        }
    }

    suspend fun getLocalStockCompanyItems(stockId: Long?): GenericEntity<List<LocalStockCompanyDaum?>?> {
        return onHandelingResponseStates("getLocalStockCompanyItems") {
            ILocalStockHandler.singleton.getLocalStockCompanyItems(stockId)
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
        return onHandelingResponseStates("getAllStatisticsLocalData") {
            ILocalStockHandler.singleton.getAllStatisticsLocalData(
                stockId,
                type,
                from,
                to,
                memId,
                authroization
            )
        }
    }

    suspend fun getAllStatisticsFodderData(
        apiToken: String?,
        from: String?,
        to: String?,
        fodderId: Long?,
        companyId: Long?,
    ): GenericEntity<StatisticsFodderData?> {
        return onHandelingResponseStates("getAllStatisticsFodderData") {
            ILocalStockHandler.singleton.getAllStatisticsFodderData(
                apiToken,
                from,
                to,
                fodderId,
                companyId
            )
        }
    }
}