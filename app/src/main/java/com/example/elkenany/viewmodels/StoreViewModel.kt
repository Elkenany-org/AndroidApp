package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.example.elkenany.api.store.IStoreImplementation
import com.example.elkenany.entities.store.AdsStoreData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StoreViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _adsStoreData = MutableLiveData<AdsStoreData?>()
    private val _loading = MutableLiveData(false)
    private val _goodToNavigate = MutableLiveData<Boolean?>(null)
    private val api = IStoreImplementation()

    val adsStoreData: LiveData<AdsStoreData?> get() = _adsStoreData
    val loading: LiveData<Boolean> get() = _loading
    val googeToNavigate: LiveData<Boolean?> get() = _goodToNavigate

    fun getAllAdsStoreData(sectorType: String, search: String?) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            _adsStoreData.value = api.getAllAdsStoreData(sectorType, search)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun navigateToCreateAdFragment() {
        _goodToNavigate.value = userApiToken != null
    }

    fun onDoneNavigating() {
        _goodToNavigate.value = null
    }
}