package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.store.IStoreImplementation
import com.example.elkenany.entities.store.AdsDetailsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AdDetailsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _adDetailsData = MutableLiveData<AdsDetailsData?>()
    private val _loading = MutableLiveData(false)
    private val api = IStoreImplementation()


    val adDetailsData: LiveData<AdsDetailsData?> get() = _adDetailsData
    val loading: LiveData<Boolean> get() = _loading

    fun getAdDetailsData(id: Long) {
        _loading.value = true
        uiScope.launch {
            _adDetailsData.value = api.getAdDetailsData(id)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}