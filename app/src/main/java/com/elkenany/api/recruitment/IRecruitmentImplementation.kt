package com.elkenany.api.recruitment

import com.elkenany.api.retrofit_configs.onHandelingResponseStates
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.*
import okhttp3.MultipartBody

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

    suspend fun addJobToFavorite(
        apiToken: String?,
        jobId: Int?,
    ): GenericEntity<AddToFavoriteData?> {
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

    suspend fun getFavoriteJobsListData(
        apiToken: String?,
    ): GenericEntity<MyFavoriteJobsListData?> {
        return onHandelingResponseStates("getMyJobsData") {
            IRecruitmentHandler.singleton.getMyFavoriteJobsListData(true, apiToken)
        }
    }

    suspend fun applyToJob(
        apiToken: String?,
        education: String?,
        experience: String?,
        jobId: Int?,
        expectedSalary: String?,
        otherInfo: String?,
        cv_file: MultipartBody.Part,
        fullName: String?,
        phone: String?,
        noticePeriod: String?,
    ): GenericEntity<ApplyData?> {
        return onHandelingResponseStates("applyToJob") {
            IRecruitmentHandler.singleton.applyToJob(apiToken,
                education,
                experience,
                jobId,
                expectedSalary,
                otherInfo,
                cv_file,
                fullName,
                phone,
                noticePeriod)
        }
    }
}