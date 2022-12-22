package com.elkenany.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.api.auth.AuthImplementation
import com.elkenany.utilities.GlobalLogicFunctions.Companion.getFCMToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONException

class RegisterViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _exception = MutableLiveData<Int>()
    private val _loading = MutableLiveData(false)


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
            Log.i("deviceToken", getFCMToken())
            _loading.value = true
            _exception.value =
                api.registerWithEmailAndPassword(name,
                    email,
                    password,
                    phone,
                    getFCMToken())
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
        _loading.value = true
        uiScope.launch {
            val response = api.reLogSocialWithGoogle(name, email, device_token, google_id)
            if (response != null) {
                _exception.value = 200
            } else {
                _exception.value = 400
            }
            _loading.value = false
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            uiScope.launch {
                signUpWithGoogle(account.givenName + account.familyName,
                    account.email,
                    getFCMToken(),
                    account.id)
                _exception.value = 200
            }
        } catch (e: ApiException) {
            Log.i("googleFailed", e.message.toString())
            _exception.value = 400
        }
    }


    @Suppress("DEPRECATION")
    fun signUpWithFacebook(fragment: Fragment, facebookCallbackManager: CallbackManager) {
        val loginManager = LoginManager.getInstance()
        loginManager.logInWithReadPermissions(fragment,
            listOf("public_profile", "email"))
        LoginManager.getInstance().registerCallback(
            facebookCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val accessToken = result.accessToken
                    val request = GraphRequest.newMeRequest(
                        accessToken
                    ) { obj, _ ->
                        try {
                            signInFacebook(obj!!.getString("name"),
                                obj.getString("email"),
                                obj.getString("id"))
                        } catch (e: JSONException) {
                            Log.i("FacebookJSONException", "failed : ${e.message.toString()}")
                            _exception.value = 400
                        }

                    }
                    val parameters = Bundle()
                    parameters.putString("fields",
                        "id,birthday,first_name,gender,last_name,name,email")
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    Log.i("FacebookCancel", "cancel")
                    _exception.value = 400
                }

                override fun onError(error: FacebookException) {
                    Log.i("FacebookException", "failed : ${error.message}")
                    _exception.value = 400
                }
            })

    }

    private fun signInFacebook(name: String?, email: String?, facebook_id: String?) {
        _loading.value = true
        uiScope.launch {
            try {
                val response =
                    api.reLogSocialWithFaceBook(name, email, getFCMToken(), facebook_id)
                if (response != null) {
                    _exception.value = 200
                } else {
                    _exception.value = 400
                    _loading.value = false
                }
            } catch (e: Exception) {
                _exception.value = 400
                _loading.value = false
            }
        }
    }
}