package com.example.elkenany.api.auth

import android.util.Log
import com.example.elkenany.entities.auth_data.UserAuthData
import retrofit2.await

@Suppress("unused")
class AuthImplementation {
    // variable to hold user data in
    companion object {
        var auth: UserAuthData? = null
        var userApiToken : String? = null
    }


    //Login with no credentials
    fun loginWithNoCredentials() {
        auth = null
    }

    //Login function using only email and password to deliver them to Api
    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String,
    ): Boolean {
        return try {
            val response = AuthHandler.singleton.loginWithEmailAndPassword(email, password).await()
            Log.i("login response", "Login response is : $response")
            userApiToken = response.data!!.apiToken
            getAllUserData(userApiToken)
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
            userApiToken = response.data!!.apiToken
            getAllUserData(userApiToken)
            true
        } catch (e: Throwable) {
            Log.i("register", "register failed : ${e.message}")
            false
        }
    }

    private suspend fun getAllUserData(api_token: String?): UserAuthData? {
        return try {
            val response = AuthHandler.singleton.getUserProfile("Bearer $api_token").await()
            auth = response.data
            auth
        } catch (e: Throwable) {
            Log.i("user_data", "${e.message}")
            auth
        }
    }
}