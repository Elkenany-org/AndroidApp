package com.elkenany.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.local_stock.ILocalStockImplementation
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.stock_data.FeedsData
import com.elkenany.entities.stock_data.LocalStockCompanyDaum
import com.elkenany.entities.stock_data.LocalStockDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocalStockDetailsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _exception = MutableLiveData<Int?>()
    private val _localStockDetailsData = MutableLiveData<LocalStockDetailsData?>()
    private val _feedsItem = MutableLiveData<FeedsData?>()
    private val _companyItem = MutableLiveData<List<LocalStockCompanyDaum?>?>()
    private val _loading = MutableLiveData(false)
    private val api = ILocalStockImplementation()


    val feedsItem: LiveData<FeedsData?> get() = _feedsItem
    val companyItem: LiveData<List<LocalStockCompanyDaum?>?> get() = _companyItem
    val localStockDetailsData: LiveData<LocalStockDetailsData?> get() = _localStockDetailsData
    val exception: LiveData<Int?> get() = _exception

    val loading: LiveData<Boolean> get() = _loading

    fun getLocalStockDetailsData(
        id: Long,
        date: String?,
        type: String,
        feedId: String?,
        companyId: String?,
    ) {
        Log.i("vm sector type", type)
        _loading.value = true
        uiScope.launch {
            if (type == "fodder") {
                _companyItem.value = api.getLocalStockCompanyItems(id).data
                _feedsItem.value = api.getLocalStockFeedsItems(id).data
            }
            val response = api.getLocalStockDetailsByIdAndType(id, date, type, feedId, companyId)
            exceptionChecker(response)
            _loading.value = false
        }
    }

    private fun exceptionChecker(response: GenericEntity<LocalStockDetailsData?>) {
        if (response.error != null) {
            _exception.value = response.error.toInt()
        } else {
            if (response.data!!.members.isEmpty()) {
                _exception.value = 404
            } else {
                _exception.value = 200
            }
            _localStockDetailsData.value = response.data
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}