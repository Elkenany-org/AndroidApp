package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.elkenany.api.local_stock.ILocalStockImplementation
import com.elkenany.entities.stock_data.StatisticsFodderData
import com.elkenany.entities.stock_data.StatisticsLocalData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StatisticsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _statisticsLocalData = MutableLiveData<StatisticsLocalData?>()
    private val _statisticsFodderData = MutableLiveData<StatisticsFodderData?>()
    private val _exception = MutableLiveData<Int>()
    private val _loading = MutableLiveData(false)
    private val api = ILocalStockImplementation()


    val statisticsLocalData: LiveData<StatisticsLocalData?> get() = _statisticsLocalData
    val statisticsFodderData: LiveData<StatisticsFodderData?> get() = _statisticsFodderData
    val exception: LiveData<Int> get() = _exception
    val loading: LiveData<Boolean> get() = _loading

    fun getFodderStockDetailsData(
        from: String?,
        to: String?,
        fodderId: Long?,
        companyId: Long?,
    ) {

        uiScope.launch {
            if (userApiToken == null) {
                _exception.value = 401
            } else {
                _loading.value = true
                val response = api.getAllStatisticsFodderData(
                    "Bearer $userApiToken",
                    from,
                    to,
                    fodderId,
                    companyId
                )
                if (response.error == "402") {
                    _exception.value = 402
                    _statisticsFodderData.value = null
                } else {
                    _exception.value = 200
                    _statisticsFodderData.value = response.data
                }
            }


            _loading.value = false
        }
    }

    fun getLocalStockDetailsData(
        stockId: Long?,
        type: String?,
        from: String?,
        to: String?,
        memId: Long?,
    ) {
        if (userApiToken == null) {
            _exception.value = 401
        } else {
            _loading.value = true
            uiScope.launch {
                val response =
                    api.getAllStatisticsLocalData(stockId,
                        type,
                        from,
                        to,
                        memId,
                        "Bearer $userApiToken")
                if (response.error == "402") {
                    _exception.value = 402
                    _statisticsFodderData.value = null
                } else {
                    _exception.value = 200
                    _statisticsLocalData.value = response.data
                }
                _loading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}