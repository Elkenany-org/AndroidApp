package com.example.elkenany.viewmodels

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation
import com.example.elkenany.api.auth.AuthImplementation.Companion.auth
import com.example.elkenany.entities.auth_data.UserAuthData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ProfileViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _userData = MutableLiveData<UserAuthData?>(null)
    private val api = AuthImplementation()

    val userData: LiveData<UserAuthData?> get() = _userData

    init {
        _userData.value = auth
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()

    }
}