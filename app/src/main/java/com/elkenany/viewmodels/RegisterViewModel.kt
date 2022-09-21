package com.elkenany.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.MainActivity
import com.elkenany.api.auth.AuthImplementation
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _exception = MutableLiveData<Int>()
    private val _loading = MutableLiveData(false)
    private val _token = MainActivity.getToken()

    private val api = AuthImplementation()
    val loading: LiveData<Boolean> get() = _loading
    val exception: LiveData<Int> get() = _exception

    fun registerAccount(
        name: String,
        email: String,
        phone: String,
        password: String,
    ) {
        uiScope.launch {
            _loading.value = true
            _exception.value = api.registerWithEmailAndPassword(name, email, password, phone,_token)
            _loading.value = false
        }
    }

    @Suppress("SameParameterValue")
    private fun signUpWithGoogle(
        name: String?,
        email: String?,
        device_token: String?,
        google_id: String?,
    ) {
        uiScope.launch {
            _loading.value = true
            api.reLogSocialWithGoogle(name, email, device_token, google_id)
            _loading.value = false
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            signUpWithGoogle(account.givenName + account.familyName,
                account.email,
                _token,
                account.id)
            Log.i("account", account.id + account.givenName + account.familyName + account.email)
            _exception.value = 200
        } catch (e: ApiException) {
            Log.i("googleFailed", e.message.toString())
            _exception.value = 400
        }
    }
}