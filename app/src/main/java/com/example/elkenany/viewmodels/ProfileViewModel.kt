package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation
import com.example.elkenany.api.auth.AuthImplementation.Companion.auth
import com.example.elkenany.entities.auth_data.AuthData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ProfileViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _authData = MutableLiveData<AuthData?>(null)
    private val api = AuthImplementation()

    val authData: LiveData<AuthData?> get() = _authData

    init {
        _authData.value = auth
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()

    }
}