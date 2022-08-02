package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.callback.IHomeImplementation
import com.example.elkenany.entities.home_data.HomeServiceData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeServiceViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _homeServiceData = MutableLiveData<HomeServiceData>()
    private val _loading = MutableLiveData(false)
    private val api = IHomeImplementation()


    val homeServiceData: LiveData<HomeServiceData> get() = _homeServiceData
    val loading: LiveData<Boolean> get() = _loading


    init {
        getHomeData()
    }

    private fun getHomeData() {
        _loading.value = true
        uiScope.launch {
            _homeServiceData.value = api.getHomeServiceData()
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}