package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.tenders.ITendersImplementation
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.tenders.TendersListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TenderSubSectionsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _responseData = MutableLiveData<TendersListData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _loading = MutableLiveData(false)
    private val api = ITendersImplementation()


    val responseData: LiveData<TendersListData?> get() = _responseData
    val loading: LiveData<Boolean> get() = _loading
    val exception: LiveData<Int?> get() = _exception


    fun getAllTendersData(
        sectorId: Long?,
        sort: Long?,
        search: String?
    ) {
        _loading.value = true
        uiScope.launch {
            val response = api.getAllTendersListData(sectorId, sort, search)
            exceptionChecker(response)
            _loading.value = false
        }
    }


    private fun exceptionChecker(response: GenericEntity<TendersListData?>) {
        if (response.error != null) {
            _exception.value = response.error.toInt()
        } else {
            if (response.data!!.data.isEmpty()) {
                _exception.value = 404
            } else {
                _exception.value = 200
            }
            _responseData.value = response.data
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}