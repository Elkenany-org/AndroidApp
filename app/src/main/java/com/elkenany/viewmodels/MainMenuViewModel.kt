package com.elkenany.viewmodels

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elkenany.SHARED_PREFRENCES
import com.elkenany.api.auth.AuthImplementation
import com.elkenany.api.auth.AuthImplementation.Companion.auth
import com.elkenany.entities.auth_data.UserAuthData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainMenuViewModel : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val _userAuth = MutableLiveData<UserAuthData?>(null)
    private val _loggedOut = MutableLiveData(false)
    private val api = AuthImplementation()
    val userAuth: LiveData<UserAuthData?> get() = _userAuth
    val loggedOut : LiveData<Boolean>get() = _loggedOut

    init {
        _userAuth.value = auth
    }

    fun signOutFromGoogle(context: Context,activity: Activity) {
        uiScope.launch {
            api.signOutFromGoogle(context)
            _loggedOut.value = true
            clearUserSharedPreferences(activity)
        }
    }
    private fun clearUserSharedPreferences(activity: Activity) {
        val sharedPreferences =
            activity.getSharedPreferences(SHARED_PREFRENCES, Context.MODE_PRIVATE)
                ?: return
        with(sharedPreferences.edit()) {
            putString("email", null)
            putString("password", null)
            apply()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()

    }


}