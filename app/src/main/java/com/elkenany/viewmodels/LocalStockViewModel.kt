package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.local_stock.ILocalStockImplementation
import com.elkenany.entities.stock_data.LocalStockData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocalStockViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _homeStockData = MutableLiveData<LocalStockData?>()
    private val _loading = MutableLiveData(false)
    private val api = ILocalStockImplementation()


    val homeStockData: LiveData<LocalStockData?> get() = _homeStockData
    val loading: LiveData<Boolean> get() = _loading

    fun getHomeStockData(sectionId: Long?, search: String?) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            _homeStockData.value = api.getLocalStockSectionsData(sectionId, search)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}