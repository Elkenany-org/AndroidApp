package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.elkenany.api.store.IStoreImplementation
import com.elkenany.entities.store.ChatsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatsViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _chatsList = MutableLiveData<ChatsData?>()
    private val _loading = MutableLiveData(false)
    private val _exception = MutableLiveData<Int>()
    private val api = IStoreImplementation()

    val chatsList: LiveData<ChatsData?> get() = _chatsList
    val exception: LiveData<Int> get() = _exception
    val loading: LiveData<Boolean> get() = _loading

    init {
        getAllChatsData()
    }

    private fun getAllChatsData() {
        _loading.value = true
        uiScope.launch {
            if (userApiToken == null) {
                _exception.value = 401
            } else {
                _chatsList.value = api.getAllChatsData("Bearer $userApiToken")
            }
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}