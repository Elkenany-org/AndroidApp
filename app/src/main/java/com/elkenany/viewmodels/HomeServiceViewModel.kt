package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation
import com.elkenany.api.callback.IHomeImplementation
import com.elkenany.entities.home_data.HomeServiceData
import com.elkenany.entities.home_data.PopUpData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeServiceViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _homeServiceData = MutableLiveData<HomeServiceData>()
    private val _popUpData = MutableLiveData<PopUpData?>()
    private val _loading = MutableLiveData(false)
    private val api = IHomeImplementation()


    val homeServiceData: LiveData<HomeServiceData> get() = _homeServiceData
    val popUpData: LiveData<PopUpData?> get() = _popUpData
    val loading: LiveData<Boolean> get() = _loading


    init {
        getHomeData()
    }

    private fun getHomeData() {
        _loading.value = true
        uiScope.launch {
            val apiToken = if (AuthImplementation.userApiToken == null) {
                null
            } else {
                "Bearer ${AuthImplementation.userApiToken}"
            }
            _homeServiceData.value = api.getHomeServiceData(apiToken)
            _popUpData.value = api.getPopUpAdData().data
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}