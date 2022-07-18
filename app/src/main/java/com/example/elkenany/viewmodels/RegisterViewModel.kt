package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _register = MutableLiveData<Boolean?>(null)
    private val _signupViaGoogle = MutableLiveData(false)
    private val _loading = MutableLiveData(false)
    private val api = AuthImplementation()

    val loading: LiveData<Boolean> get() = _loading
    val loginViewGoogle: LiveData<Boolean> get() = _signupViaGoogle
    val register: LiveData<Boolean?> get() = _register

    fun registerAccount(
        name: String,
        email: String,
        phone: String,
        password: String,
        deviceToken: String,
    ) {
        uiScope.launch {
            _loading.value = true
            _register.value =
                api.registerWithEmailAndPassword(name, email, password, phone, deviceToken)
            _loading.value = false
        }
    }
}