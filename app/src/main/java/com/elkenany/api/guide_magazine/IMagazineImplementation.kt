package com.elkenany.api.guide_magazine

import android.util.Log
import com.elkenany.api.retrofit_configs.onHandelingResponseStates
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.guide_magazine.MagazineData
import com.elkenany.entities.guide_magazine.MagazineDetailsData
import retrofit2.HttpException
import retrofit2.await
import java.net.SocketTimeoutException

class IMagazineImplementation {
    suspend fun getAllMagazineData(
        sectorType: String?,
        sort: Long?,
        cityId: Long?,
        search: String?,
    ): MagazineData? {
        return try {
            val response =
                IMagazineHandler.singleton.getAllMagazineListData(true,
                    sectorType,
                    sort,
                    cityId,
                    search)
                    .await()
            response.data
        } catch (e: HttpException) {
            Log.i("getAllMagazineData", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getAllMagazineData", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("getAllMagazineData", e.message.toString())
            null
        }
    }

    suspend fun rateThisMagazine(
        apiToken: String?,
        magazineId: Long?,
        rate: Long?,
    ): GenericEntity<Any?> {
        return onHandelingResponseStates("rateThisMagazine") {
            IMagazineHandler.singleton.rateThisMagazine(apiToken, magazineId, rate)
        }
    }

    suspend fun getMagazineDetailsData(magazineId: Long?): MagazineDetailsData? {
        return try {
            val response =
                IMagazineHandler.singleton.getMagazineItemData(magazineId)
                    .await()
            response.data
        } catch (e: HttpException) {
            Log.i("getAllMagazineData", e.message.toString())
            null
        } catch (e: SocketTimeoutException) {
            Log.i("getAllMagazineData", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("getAllMagazineData", e.message.toString())
            null
        }
    }
}