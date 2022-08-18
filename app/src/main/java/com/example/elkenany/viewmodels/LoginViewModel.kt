@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.elkenany.viewmodels

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elkenany.api.auth.AuthImplementation
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONException


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
                signInGoogle(null, account.email, "1", account.id)
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
    fun signInGoogle(name: String?, email: String?, device_token: String?, google_id: String?) {
        _loading.value = true
        uiScope.launch {
            try {
                val response =
                    api.reLogSocialWithGoogle(name, email, device_token, google_id)
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

    fun signInFacebook(name: String?, email: String?, device_token: String?, facebook_id: String?) {
        _loading.value = true
        uiScope.launch {
            try {
                val response =
                    api.reLogSocialWithFaceBook(name, email, device_token, facebook_id)
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

    @Suppress("DEPRECATION")
    fun signInWithFaceBook(fragment: Fragment, facebookCallbackManager: CallbackManager) {
        val loginManager = LoginManager.getInstance()
        loginManager.logInWithReadPermissions(fragment,
            listOf("public_profile", "email"))
        LoginManager.getInstance().registerCallback(
            facebookCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val accessToken = result.accessToken
                    Log.i("LoginInformation", "success $accessToken")
                    val request = GraphRequest.newMeRequest(
                        accessToken
                    ) { obj, _ ->
                        try {
                            signInFacebook(obj!!.getString("name"),
                                obj.getString("email"),
                                "1",
                                obj.getString("id"))
                        } catch (e: JSONException) {
                            Log.i("LoginInformation", "failed : ${e.message.toString()}")
                        }

                    }
                    val parameters = Bundle()
                    parameters.putString("fields",
                        "id,birthday,first_name,gender,last_name,name,email")
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    Log.i("LoginInformation", "cancel")
                }

                override fun onError(error: FacebookException) {
                    Log.i("LoginInformation", "failure")
                }
            })

    }

    //this function handles the signin with google results
    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            signInGoogle("${account.givenName} + ${account.familyName}",
                "${account.email}",
                "1",
                "${account.id}")
            Log.i("account", account.toString())
        } catch (e: ApiException) {
            Log.i("googleFailed", e.message.toString())
        }
    }
}