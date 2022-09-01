package com.example.elkenany.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation
import com.example.elkenany.entities.auth_data.PasswordRecoveryData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _passwordRecoveryCode = MutableLiveData<PasswordRecoveryData?>()
    private val _loading = MutableLiveData(false)
    private val api = AuthImplementation()


    val passwordRecoveryCode: LiveData<PasswordRecoveryData?> get() = _passwordRecoveryCode
    val loading: LiveData<Boolean> get() = _loading


    fun getVerificationCodeForEmail(email: String?) {
        _loading.value = true
        uiScope.launch {
            _passwordRecoveryCode.value = api.recoverPasswordWithEmail(email)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}