package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation
import com.example.elkenany.api.auth.AuthImplementation.Companion.auth
import com.example.elkenany.entities.auth_data.UserAuthData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainMenuViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _userAuth = MutableLiveData<UserAuthData?>(null)
    private val api = AuthImplementation()
    val userAuth: LiveData<UserAuthData?> get() = _userAuth

    init {
        _userAuth.value = auth
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()

    }
}