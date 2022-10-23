package com.elkenany.api.recruitment

import com.elkenany.api.retrofit_configs.onHandelingResponseStates
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.JobsData

class IRecruitmentImplementation {

    suspend fun getAllJobsData(
        sort: Int?,
        category: String?,
        search: String?,
    ): GenericEntity<JobsData?> {
        return onHandelingResponseStates("getAllJobsData") {
            IRecruitmentHandler.singleton.getAllJobsData(sort, category, search)
        }
    }
}