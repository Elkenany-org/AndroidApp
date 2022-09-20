package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.elkenany.api.store.IStoreImplementation
import com.elkenany.entities.store.MyAdsListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyAdsListViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _myAdsList = MutableLiveData<MyAdsListData?>()
    private val _exception = MutableLiveData<Int>()
    private val _goodToNavigate = MutableLiveData<Boolean?>(null)
    private val _loading = MutableLiveData(false)
    private val api = IStoreImplementation()

    val myAdsList: LiveData<MyAdsListData?> get() = _myAdsList
    val loading: LiveData<Boolean> get() = _loading
    val exception: LiveData<Int> get() = _exception
    val googeToNavigate: LiveData<Boolean?> get() = _goodToNavigate

    fun getAllNewsData(sectorType: String?) {
        _loading.value = true
        if (userApiToken == null) {
            _exception.value = 401
        } else {
            uiScope.launch {
                val response = api.getAllMyAdsListData("Bearer $userApiToken", sectorType)
                if (response != null) {
                    if (response.data!!.data.isEmpty()) {
                        _exception.value = 404
                        _myAdsList.value = null
                    } else if (response.data.data.isNotEmpty()) {
                        _exception.value = 200
                        _myAdsList.value = response.data
                    }

                } else {
                    _exception.value = 400
                }
                _loading.value = false
            }
        }
    }

    fun deleteAdFromDataBase(id: Long?) {
        _loading.value = true
        if (userApiToken == null) {
            _exception.value = 401
        } else {
            uiScope.launch {
                val response = api.deleteAdFromDataBase("Bearer $userApiToken", id)
                if (response != null) {
                    if (response == false) {
                        _exception.value = 104
                    } else if (response == true) {
                        _exception.value = 100
                    }

                } else {
                    _exception.value = 400
                }
                _loading.value = false
            }
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