package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.callback.DataReceiverImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocalStockViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _homeStockData = MutableLiveData<Any>()
    private val _loading = MutableLiveData(false)
    private val api = DataReceiverImplementation()


    val homeStockData: LiveData<Any> get() = _homeStockData
    val loading: LiveData<Boolean> get() = _loading


    fun getHomeStockData(sectorType: String) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}