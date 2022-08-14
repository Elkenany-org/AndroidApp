@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.elkenany.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
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

    //this function fires to check if there is login instance already from google account
    fun initViewModel(context: Context) {
        uiScope.launch {
            val account = GoogleSignIn.getLastSignedInAccount(context)
            if (account != null) {
                signInWithGoogle(null, account.email, "1", account.id)
            }
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        uiScope.launch {
            _loading.value = true
            _login.value = api.loginWithEmailAndPassword(email, password)
            _loading.value = false
        }
    }

    //this function fires when the user wants to sign in with google account
    fun signInWithGoogle(name: String?, email: String?, device_token: String?, google_id: String?) {
        _loading.value = true
        uiScope.launch {
            try {
                val response =
                    api.reLogSocialWithGoogleOrFaceBook(name, email, device_token, google_id)
                if (response != null) {
                    _login.value = true
                } else {
                    _login.value = false
                    _loading.value = false
                }
            } catch (e: Exception) {
                _login.value = false
                _loading.value = false
            }
        }
    }

//    fun signInWithFacebook() {
//        // ToDo --> implement signin with facebook provider
//    }

    fun signInWithGuestAccount() {
        _login.value = true
        api.loginWithNoCredentials()
    }

//    fun didForgetPassword() {
//        // ToDo --> implement password recovery function
//    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()

    }

    //this function handles the signin with google results
    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            signInWithGoogle("${account.givenName} + ${account.familyName}",
                "${account.email}",
                "1",
                "${account.id}")
            Log.i("account", account.toString())
        } catch (e: ApiException) {
            Log.i("googleFailed", e.message.toString())
        }
    }
}