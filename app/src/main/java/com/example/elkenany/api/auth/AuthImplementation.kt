package com.example.elkenany.api.auth

import android.content.Context
import android.util.Log
import com.example.elkenany.api.retrofit_configs.GoogleAuth_Config
import com.example.elkenany.entities.auth_data.AuthData
import com.example.elkenany.entities.auth_data.PasswordRecoveryData
import com.example.elkenany.entities.auth_data.UserAuthData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import retrofit2.HttpException
import retrofit2.await
import java.net.SocketTimeoutException

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
            val response = AuthHandler.singleton.reLogSocialWithGoogle(
                name,
                email,
                device_token, google_id
            ).await()
            Log.i("login response", "Login ${response.data}")
            userApiToken = response.data!!.apiToken
            getAllUserData(response.data.apiToken)
            response.data
        } catch (e: HttpException) {
            Log.i("reLogSocialWithGoogle", "Login with google failed : ${e.message}")
            null
        } catch (e: SocketTimeoutException) {
            Log.i("reLogSocialWithGoogle", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("reLogSocialWithGoogle", e.message.toString())
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
            val response = AuthHandler.singleton.reLogSocialWithFacebook(
                name,
                email,
                device_token, facebook_id
            ).await()
            Log.i("login response", "Login ${response.data}")
            userApiToken = response.data!!.apiToken
            getAllUserData(response.data.apiToken)
            response.data
        } catch (e: HttpException) {
            Log.i("reLogSocialWithFaceBook", "Login with google failed : ${e.message}")
            null
        } catch (e: SocketTimeoutException) {
            Log.i("reLogSocialWithFaceBook", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("reLogSocialWithFaceBook", e.message.toString())
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
            Log.i("loginWithEmailAndPassword", e.code().toString())
            e.code()
        } catch (e: SocketTimeoutException) {
            Log.i("loginWithEmailAndPassword", e.message.toString())
            400
        } catch (e: Exception) {
            Log.i("loginWithEmailAndPassword", e.message.toString())
            400
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
            val response = AuthHandler.singleton.registerWithEmailAndPasswordWithPhone(
                name,
                email,
                password,
                phone,
                deviceToken
            )
                .await()
            userApiToken = response.data!!.apiToken
            getAllUserData(userApiToken)
            true
        } catch (e: HttpException) {
            Log.i("registerWithEmailAndPassword", "register failed : ${e.message}")
            false
        } catch (e: SocketTimeoutException) {
            Log.i("registerWithEmailAndPassword", e.message.toString())
            false
        } catch (e: Exception) {
            Log.i("registerWithEmailAndPassword", e.message.toString())
            false
        }
    }

    private suspend fun getAllUserData(api_token: String?): UserAuthData? {
        return try {
            val response = AuthHandler.singleton.getUserProfile("Bearer $api_token").await()
            Log.i("user_data", response.data.toString())
            auth = response.data
            auth
        } catch (e: HttpException) {
            Log.i("getAllUserData", "${e.message}")
            auth
        } catch (e: SocketTimeoutException) {
            Log.i("getAllUserData", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("getAllUserData", e.message.toString())
            null
        }
    }

    fun signOutFromGoogle(context: Context) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            val gsc = GoogleSignIn.getClient(context, GoogleAuth_Config.gso)
            gsc.signOut()
        }
    }

    suspend fun recoverPasswordWithEmail(email: String?): PasswordRecoveryData? {
        return try {
            val response = AuthHandler.singleton.recoverPasswordWithEmail(email).await()
            response.data
        } catch (e: HttpException) {
            Log.i("recoverPasswordWithEmail", "Login with google failed : ${e.message}")
            null
        } catch (e: SocketTimeoutException) {
            Log.i("recoverPasswordWithEmail", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("recoverPasswordWithEmail", e.message.toString())
            null
        }
    }

    suspend fun onSuccessResetPassword(
        email: String?,
        code: String?,
        password: String?
    ): AuthData? {
        return try {
            val response = AuthHandler.singleton.onSuccessResetPassword(
                email,
                code, password
            ).await()
            userApiToken = response.data!!.apiToken
            getAllUserData(response.data.apiToken)
            response.data
        } catch (e: HttpException) {
            Log.i("onSuccessResetPassword", "Login with google failed : ${e.message}")
            null
        } catch (e: SocketTimeoutException) {
            Log.i("onSuccessResetPassword", e.message.toString())
            null
        } catch (e: Exception) {
            Log.i("onSuccessResetPassword", e.message.toString())
            null
        }
    }
}