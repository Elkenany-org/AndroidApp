package com.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation.Companion.userApiToken
import com.elkenany.api.home.IHomeImplementation
import com.elkenany.entities.home_data.NotificationsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val api = IHomeImplementation()
    private val _loading = MutableLiveData(false)
    private val _notificationList = MutableLiveData<NotificationsData?>()
    private val _authorized = MutableLiveData(false)


    val notificationList: LiveData<NotificationsData?> get() = _notificationList
    val authorized: LiveData<Boolean> get() = _authorized
    val loading: LiveData<Boolean> get() = _loading


    fun onGettingNotificationData() {
        val apiToken = userApiToken.toString()
        _loading.value = true
        uiScope.launch {
            if (userApiToken != null) {
                _authorized.value = true
                _notificationList.value = api.getAllNotificationData("Bearer $apiToken")
            } else {
                _authorized.value = false
            }

            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}