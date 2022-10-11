package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.ships.IShipsImplementation
import com.elkenany.entities.ships.ShipsStatisticsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShipsStatisticsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _shipsData = MutableLiveData<ShipsStatisticsData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _loading = MutableLiveData(false)
    private val api = IShipsImplementation()

    val shipsData: LiveData<ShipsStatisticsData?> get() = _shipsData
    val exception: LiveData<Int?> get() = _exception
    val loading: LiveData<Boolean> get() = _loading

    fun getAllStatisticsData(
        type: String?,
        from: String?,
        to: String?,
        country: String?,
    ) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            val response = api.getShipsStatisticsData(type, from, to, country)
            if (response != null) {
                _shipsData.value = response
                _exception.value = 200
            }
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}