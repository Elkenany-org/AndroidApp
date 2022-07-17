package com.example.elkenany.api.auth

import android.util.Log
import com.example.elkenany.entities.auth_data.AuthData
import retrofit2.await

@Suppress("unused")
class AuthImplementation {
    // variable to hold user data in
    private var _auth: AuthData? = null
    var auth = _auth

    //Login with no credentials
    fun loginWithNoCredentials() {
        _auth = null
    }

    //Login function using only email and password to deliver them to Api
    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String,
    ): Boolean {
        return try {
            val response = AuthHandler.singleton.loginWithEmailAndPassword(email, password).await()
            _auth = response.data
            Log.i("login response", "Login response is : $response")
            true
        } catch (e: Throwable) {
            Log.i("login response", "Login failed : ${e.message}")
            false
        }
    }

    //Register function using email,password,name.phone,deviceToken to deliver them to Api
    suspend fun registerWithEmailAndPassword(
        name: String,
        email: String,
        password: String,
        phone: String,
        deviceToken: String,
    ): Boolean {
        return try {
            val response = AuthHandler.singleton.registerWithEmailAndPasswordWithPhone(name,
                email,
                password,
                phone,
                deviceToken)
                .await()
            _auth = response.data
            true
        } catch (e: Throwable) {
            Log.i("register", "register failed : ${e.message}")
            false
        }
    }
}