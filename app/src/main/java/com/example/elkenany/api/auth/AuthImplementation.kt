package com.example.elkenany.api.auth

import android.content.Context
import android.util.Log
import com.example.elkenany.api.retrofit_configs.GoogleAuth_Config
import com.example.elkenany.entities.auth_data.AuthData
import com.example.elkenany.entities.auth_data.UserAuthData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import retrofit2.HttpException
import retrofit2.await

@Suppress("unused")
class AuthImplementation {
    // variable to hold user data in
    companion object {
        var auth: UserAuthData? = null
        var userApiToken: String? = null
    }


    //Login with no credentials
    fun loginWithNoCredentials() {
        userApiToken = null
        auth = null
    }

    suspend fun reLogSocialWithGoogle(
        name: String?,
        email: String?,
        device_token: String?,
        google_id: String?,
    ): AuthData? {
        return try {
            val response = AuthHandler.singleton.reLogSocialWithGoogle(name,
                email,
                device_token, google_id).await()
            Log.i("login response", "Login ${response.data}")
            userApiToken = response.data!!.apiToken
            getAllUserData(response.data.apiToken)
            response.data
        } catch (e: Throwable) {
            Log.i("login response", "Login with google failed : ${e.message}")
            null
        }
    }

    suspend fun reLogSocialWithFaceBook(
        name: String?,
        email: String?,
        device_token: String?,
        facebook_id: String?,
    ): AuthData? {
        return try {
            val response = AuthHandler.singleton.reLogSocialWithFacebook(name,
                email,
                device_token, facebook_id).await()
            Log.i("login response", "Login ${response.data}")
            userApiToken = response.data!!.apiToken
            getAllUserData(response.data.apiToken)
            response.data
        } catch (e: Throwable) {
            Log.i("login response", "Login with google failed : ${e.message}")
            null
        }
    }

    //Login function using only email and password to deliver them to Api
    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String,
    ): Int {
        return try {
            val response = AuthHandler.singleton.loginWithEmailAndPassword(email, password).await()
            Log.i("login response", "Login response is : $response")
            userApiToken = response.data!!.apiToken
            getAllUserData(userApiToken)
            200
        } catch (e: HttpException) {
            e.code()
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
            Log.i("user_data", response.data.toString())
            auth = response.data
            auth
        } catch (e: Throwable) {
            Log.i("user_data", "${e.message}")
            auth
        }
    }

    fun signOutFromGoogle(context: Context) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            val gsc = GoogleSignIn.getClient(context, GoogleAuth_Config.gso)
            gsc.signOut()
        }
    }
}