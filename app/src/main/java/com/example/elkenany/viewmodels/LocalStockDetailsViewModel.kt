package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.local_stock.ILocalStockImplementation
import com.example.elkenany.entities.stock_data.LocalStockDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocalStockDetailsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _localStockDetailsData = MutableLiveData<LocalStockDetailsData?>()
    private val _loading = MutableLiveData(false)
    private val api = ILocalStockImplementation()


    val localStockDetailsData: LiveData<LocalStockDetailsData?> get() = _localStockDetailsData

    val loading: LiveData<Boolean> get() = _loading

    fun getLocalStockDetailsData(id: Long, date: String?, type: String) {
        _loading.value = true
        uiScope.launch {
            _localStockDetailsData.value = api.getLocalStockDetailsByIdAndType(id, date,type)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}