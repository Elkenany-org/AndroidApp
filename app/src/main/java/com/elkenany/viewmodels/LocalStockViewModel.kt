package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.local_stock.ILocalStockImplementation
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.stock_data.LocalStockData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocalStockViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _homeStockData = MutableLiveData<LocalStockData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _loading = MutableLiveData(false)
    private val api = ILocalStockImplementation()


    val homeStockData: LiveData<LocalStockData?> get() = _homeStockData
    val loading: LiveData<Boolean> get() = _loading
    val exception: LiveData<Int?> get() = _exception

    fun getHomeStockData(sectionId: Long?, search: String?) {
        _loading.value = true
        uiScope.launch {
            val response = api.getLocalStockSectionsData(sectionId, search)
            exceptionChecker(response)
            _loading.value = false
        }
    }

    private fun exceptionChecker(response: GenericEntity<LocalStockData?>) {
        if (response.error != null) {
            _exception.value = response.error.toInt()
        } else {
            if (response.data!!.subSections.isNullOrEmpty()) {
                _exception.value = 404
            } else {
                _exception.value = 200
            }
            _homeStockData.value = response.data
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}