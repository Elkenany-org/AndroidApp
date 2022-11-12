package com.elkenany.api.tenders

import com.elkenany.api.retrofit_configs.onHandelingResponseStates
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.tenders.TendersDetailsData
import com.elkenany.entities.tenders.TendersListData
import com.elkenany.entities.tenders.TendersSectionsListData

class ITendersImplementation {

    suspend fun getAllTendersMainSectionsData(): GenericEntity<TendersSectionsListData?> {
        return onHandelingResponseStates("getAllTendersMainSectionsData") {
            ITendersHandler.singleton.getAllTendersSectionsData(true)
        }
    }

    suspend fun getAllTendersListData(
        sectionId: Long?,
        sort: Long?,
        search: String?
    ): GenericEntity<TendersListData?> {
        return onHandelingResponseStates("getAllTendersListData") {
            ITendersHandler.singleton.getAllTendersListData(sectionId, sort, search)
        }
    }

    suspend fun getTenderDetailsData(id: Long?): GenericEntity<TendersDetailsData?> {
        return onHandelingResponseStates("getTenderDetailsData") {
            ITendersHandler.singleton.getTendersDetailsData(id)
        }
    }

}