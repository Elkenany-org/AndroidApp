package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.store.IStoreImplementation
import com.elkenany.entities.store.AdsStoreData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StoreViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _adsStoreData = MutableLiveData<AdsStoreData?>()
    private val _openCloseSearch = MutableLiveData(false)
    private val _loading = MutableLiveData(false)
    private val api = IStoreImplementation()

    val adsStoreData: LiveData<AdsStoreData?> get() = _adsStoreData
    val loading: LiveData<Boolean> get() = _loading
    val openCloseSearch: LiveData<Boolean> get() = _openCloseSearch

    private var isOpened = false
    fun getAllAdsStoreData(sectorType: Long?, search: String?) {
        _loading.value = true
        uiScope.launch {
            // ToDo --> implementing getHomeStockData(sectorType) function here
            _adsStoreData.value = api.getAllAdsStoreData(sectorType, search, "android")
            _loading.value = false
        }
    }

    fun openCloseSearchBar() {
        isOpened = !isOpened
        _openCloseSearch.value = isOpened
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}