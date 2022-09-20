package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.callback.IHomeImplementation
import com.elkenany.entities.home_data.HomeSectorsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeSectorViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _homeSectorsData = MutableLiveData<HomeSectorsData>()
    private val _loading = MutableLiveData(false)
    private val api = IHomeImplementation()


    val homeSectorsData: LiveData<HomeSectorsData> get() = _homeSectorsData
    val loading: LiveData<Boolean> get() = _loading


    init {
        getHomeData()
    }

    private fun getHomeData() {
        _loading.value = true
        uiScope.launch {
            _homeSectorsData.value = api.getHomeSectorsData()
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}