package com.elkenany.api.recruitment

import com.elkenany.api.retrofit_configs.onHandelingResponseStates
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.AddToFavoriteData
import com.elkenany.entities.recruitment.JobDetailsData
import com.elkenany.entities.recruitment.JobsData
import com.elkenany.entities.recruitment.MyJobsData

class IRecruitmentImplementation {

    suspend fun getAllJobsData(
        sort: Int?,
        category: String?,
        search: String?,
    ): GenericEntity<JobsData?> {
        return onHandelingResponseStates("getAllJobsData") {
            IRecruitmentHandler.singleton.getAllJobsData(true, sort, category, search)
        }
    }

    suspend fun addJobToFavorite(apiToken: String?, jobId: Int?): GenericEntity<AddToFavoriteData?> {
        return onHandelingResponseStates("addJobToFavorite") {
            IRecruitmentHandler.singleton.addJobToMyFavorites(apiToken, jobId)
        }
    }

    suspend fun getJobDetailsData(jobId: Int?): GenericEntity<JobDetailsData?> {
        return onHandelingResponseStates("getJobDetailsData") {
            IRecruitmentHandler.singleton.getJobDetailsData(jobId)
        }
    }

    suspend fun getMyJobsData(
        apiToken: String?,
    ): GenericEntity<MyJobsData?> {
        return onHandelingResponseStates("getMyJobsData") {
            IRecruitmentHandler.singleton.getMyJobsList(true, apiToken)
        }
    }
}