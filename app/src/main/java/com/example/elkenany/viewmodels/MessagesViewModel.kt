package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation
import com.example.elkenany.api.store.IStoreImplementation
import com.example.elkenany.entities.store.MessagesList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MessagesViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _messageList = MutableLiveData<MessagesList?>()
    private val _loading = MutableLiveData(false)
    private val _messageIndicator = MutableLiveData(false)
    private val _exception = MutableLiveData<Int>()
    private val api = IStoreImplementation()

    val messageList: LiveData<MessagesList?> get() = _messageList
    val exception: LiveData<Int> get() = _exception
    val messageIndicatior: LiveData<Boolean> get() = _messageIndicator
    val loading: LiveData<Boolean> get() = _loading

    fun getAllChatsData(id: Long) {
        _loading.value = true
        uiScope.launch {
            if (AuthImplementation.userApiToken == null) {
                _exception.value = 401
            } else {
                _messageList.value =
                    api.getAllMessagesData(id, "Bearer ${AuthImplementation.userApiToken}")!!.chat
            }
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun sendMessageData(id: Long, message: String) {
        _messageIndicator.value = true
        uiScope.launch {
            if (AuthImplementation.userApiToken == null) {
                _exception.value = 401
                _messageIndicator.value = false
            } else {
                val success = api.sendMessageData("Bearer ${AuthImplementation.userApiToken}",
                    id,
                    message)
                if (success != null) {
                    _messageList.value =
                        api.getAllMessagesData(id,
                            "Bearer ${AuthImplementation.userApiToken}")!!.chat
                    _messageIndicator.value = false
                } else {
                    _messageIndicator.value = true
                }
            }
            _loading.value = false
        }
    }
}