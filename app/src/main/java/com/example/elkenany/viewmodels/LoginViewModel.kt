package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _login = MutableLiveData<Boolean?>(null)
    private val _loading = MutableLiveData(false)
    private val api = AuthImplementation()

    val loading: LiveData<Boolean> get() = _loading
    val login: LiveData<Boolean?> get() = _login

    fun signInWithEmailAndPassword(email: String, password: String) {
        uiScope.launch {
            _loading.value = true
            _login.value = api.loginWithEmailAndPassword(email, password)
            _loading.value = false
        }
    }

    fun signInWithGoogle() {
        // ToDo --> implement signin with google provider
    }

    fun signInWithFacebook() {
        // ToDo --> implement signin with facebook provider
    }

    fun signInWithGuestAccount() {
        _login.value = true
        api.loginWithNoCredentials()
    }

    fun didForgetPassword() {
        // ToDo --> implement password recovery function
    }

    fun onDoneNavigating() {
        _login.value = false
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()

    }
}