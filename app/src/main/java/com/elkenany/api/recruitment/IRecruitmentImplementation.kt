package com.elkenany.api.recruitment

import com.elkenany.api.retrofit_configs.onHandelingResponseStates
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.recruitment.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class IRecruitmentImplementation {

    suspend fun getAllJobsData(
        apiToken: String?,
        sort: Int?,
        category: String?,
        search: String?,
    ): GenericEntity<JobsData?> {
        return onHandelingResponseStates("getAllJobsData") {
            IRecruitmentHandler.singleton.getAllJobsData(true, apiToken, sort, category, search)
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
            // turning all params to requestbody to send the types text/plain instead of jsonObjects
            IRecruitmentHandler.singleton.applyToJob(
                apiToken,
                RequestBody.create(MediaType.parse("text/plain"), education.toString()),
                RequestBody.create(MediaType.parse("text/plain"), experience.toString()),
                RequestBody.create(MediaType.parse("text/plain"), jobId.toString()),
                RequestBody.create(MediaType.parse("text/plain"), expectedSalary.toString()),
                RequestBody.create(MediaType.parse("text/plain"), otherInfo.toString()),
                cv_file,
                RequestBody.create(MediaType.parse("text/plain"), fullName.toString()),
                RequestBody.create(MediaType.parse("text/plain"), phone.toString()),
                RequestBody.create(MediaType.parse("text/plain"), noticePeriod.toString())
            )
        }
    }

    suspend fun addNewJob(
        apiToken: String?,
        jobTitle: String?,
        jobDescription: String?,
        jobSalary: String?,
        jobPhone: String?,
        jobEmail: String?,
        jobAdress: String?,
        requiredExperience: String?,
        categoryId: Long?,
        companyId: Long?,
        workHours: String?,
    ): GenericEntity<AddNewJobData?> {
        return onHandelingResponseStates("addNewJob") {
            IRecruitmentHandler.singleton.addNewJob(apiToken,
                jobTitle,
                jobDescription,
                jobSalary,
                jobPhone,
                jobEmail,
                jobAdress,
                requiredExperience,
                categoryId,
                companyId,
                workHours)
        }
    }

    suspend fun getJobApplicanstListData(
        apiToken: String?,
        jobId: Long?,
        select: Long?,
        search: String?,
    ): GenericEntity<JobApplicantsListData?> {
        return onHandelingResponseStates("getJobApplicanstListData") {
            IRecruitmentHandler.singleton.getAllApplicants(true, apiToken, jobId, select, search)
        }
    }

    suspend fun getApplicationDetailsData(
        apiToken: String?,
        appId: Long?,
    ): GenericEntity<ApplicationDetailsData?> {
        return onHandelingResponseStates("getApplicationDetailsData") {
            IRecruitmentHandler.singleton.getApplicationDetailsData(true, apiToken, appId)
        }
    }
}