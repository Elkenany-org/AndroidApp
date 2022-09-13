package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//import com.example.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.example.elkenany.api.store.IStoreImplementation
import com.example.elkenany.entities.store.AdsDetailsData
import com.example.elkenany.entities.store.StartChatDaum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AdDetailsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _adDetailsData = MutableLiveData<AdsDetailsData?>()
    private val _startChatData = MutableLiveData<StartChatDaum?>()
    private val _exception = MutableLiveData<Int>()
    private val _loading = MutableLiveData(false)
    private val api = IStoreImplementation()


    val adDetailsData: LiveData<AdsDetailsData?> get() = _adDetailsData
    val startChatData: LiveData<StartChatDaum?> get() = _startChatData
    val exception: LiveData<Int> get() = _exception
    val loading: LiveData<Boolean> get() = _loading

    fun getAdDetailsData(id: Long) {
        _loading.value = true
        uiScope.launch {
            _adDetailsData.value = api.getAdDetailsData(id)
            _loading.value = false
        }
    }

//    fun startChat(id: Long?) {
//        // present loading number
//        _exception.value = 100
//        if (userApiToken == null) {
//        // present unAuthorized number
//            _exception.value = 401
//        } else {
//            uiScope.launch {
//                _startChatData.value = api.getAllStartChatData("Bearer $userApiToken", id)
//                _exception.value = 110
//            }
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}