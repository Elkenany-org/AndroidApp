package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.home.IHomeImplementation
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.home_data.SponsersListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SponsersViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _responseData = MutableLiveData<SponsersListData?>()
    private val _exception = MutableLiveData<Int?>()
    private val _loading = MutableLiveData(false)
    private val api = IHomeImplementation()


    val responseData: LiveData<SponsersListData?> get() = _responseData
    val loading: LiveData<Boolean> get() = _loading
    val exception: LiveData<Int?> get() = _exception

    init {
        getAllSponsersData()
    }

    private fun getAllSponsersData() {
        _loading.value = true
        uiScope.launch {
            val response = api.getAllSponsersData()
            exceptionChecker(response)
            _loading.value = false
        }
    }


    private fun exceptionChecker(response: GenericEntity<SponsersListData?>) {
        if (response.error != null) {
            _exception.value = response.error.toInt()
        } else {
            if (response.data!!.logos.isEmpty()) {
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