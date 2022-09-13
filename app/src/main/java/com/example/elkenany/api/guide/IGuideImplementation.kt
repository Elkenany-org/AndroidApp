package com.example.elkenany.api.guide

import android.util.Log
import com.example.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.example.elkenany.entities.guide.CompaniesData
import com.example.elkenany.entities.guide.CompanyDetailsData
import com.example.elkenany.entities.guide.GuideData
import com.example.elkenany.entities.guide.GuideFiltersData
import com.example.elkenany.entities.store.RatingData
import retrofit2.HttpException
import retrofit2.await
import java.net.SocketTimeoutException

class IGuideImplementation {

    suspend fun getAllGuideData(sectorType: String?, search: String?): GuideData? {
        return try {
            val response = IGuideHandler.singleton.getAllGuideData(sectorType, search).await()
            response.data
        } catch (e: HttpException) {
            Log.i("getAllGuideData", e.message.toString())
            null

        }
    }

    suspend fun getAllCompaniesData(
        sectionId: Long,
        search: String?,
        countryId: Long?,
        cityId: Long?,
    ): CompaniesData? {
        return try {
            val response =
                IGuideHandler.singleton.getAllCompaniesData(sectionId,
                    search,
                    countryId,
                    cityId,
                    "android").await()
            response.data
        } catch (e: HttpException) {
            Log.i("getAllCompaniesData", e.message.toString())
            null

        }catch (e: SocketTimeoutException) {
            Log.i("getAllStatisticsLocalData", e.message.toString())
            null
        }
    }

    suspend fun getCompanyData(id: Long): CompanyDetailsData? {
        return try {
            val response =
                IGuideHandler.singleton.getCompanyData(id, "Bearer $userApiToken").await()
            response.data
        } catch (e: HttpException) {
            Log.i("getCompanyData", e.message.toString())
            null
        }catch (e: SocketTimeoutException) {
            Log.i("getAllStatisticsLocalData", e.message.toString())
            null
        }
    }

    suspend fun rateThisCompany(apiToken: String?, companyId: Long, rating: Long?): RatingData? {
        return try {
            val response =
                IGuideHandler.singleton.rateThisCompany(apiToken, companyId, rating).await()
            response.data
        } catch (e: HttpException) {
            Log.i("getCompanyData", e.code().toString())
            null
        }catch (e: SocketTimeoutException) {
            Log.i("getAllStatisticsLocalData", e.message.toString())
            null
        }
    }

    suspend fun getGuideFilterData(sectionId: Long?): GuideFiltersData? {
        return try {
            val response =
                IGuideHandler.singleton.getGuideFilterData(sectionId).await()
            response.data
        } catch (e: HttpException) {
            Log.i("getGuideFilterData", e.code().toString())
            null
        }catch (e: SocketTimeoutException) {
            Log.i("getAllStatisticsLocalData", e.message.toString())
            null
        }
    }

}