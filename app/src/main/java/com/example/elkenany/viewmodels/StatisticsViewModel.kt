package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.example.elkenany.api.local_stock.ILocalStockImplementation
import com.example.elkenany.entities.stock_data.StatisticsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StatisticsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _statisticsData = MutableLiveData<StatisticsData?>()
    private val _loading = MutableLiveData(false)
    private val api = ILocalStockImplementation()


    val statisticsData: LiveData<StatisticsData?> get() = _statisticsData

    val loading: LiveData<Boolean> get() = _loading

    fun getLocalStockDetailsData(
        stockId: Long?,
        type: String?,
        from: String?,
        to: String?,
        memId: Long?,
    ) {
        _loading.value = true
        uiScope.launch {
            _statisticsData.value =
                api.getAllStatisticsData(stockId, type, from, to,memId, "Bearer $userApiToken")
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}