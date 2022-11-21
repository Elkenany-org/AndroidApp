package com.elkenany.api.auth

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.auth_data.AuthData
import com.elkenany.entities.auth_data.PasswordRecoveryData
import com.elkenany.entities.auth_data.UserAuthData
import retrofit2.Call
import retrofit2.http.*

interface IAuth {

    //Login using the api service
    @FormUrlEncoded
    @POST("login")
    fun loginWithEmailAndPassword(
        @Header("android") isAndoird: Boolean,
        @Field("device_token") fcmToken: String?,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<GenericEntity<AuthData?>>

    //Register using the api service
    @FormUrlEncoded
    @POST("register")
    fun registerWithEmailAndPasswordWithPhone(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
        @Field("device_token") deviceToken: String,
    ): Call<GenericEntity<AuthData?>>

    @GET("profile")
    fun getUserProfile(@Header("Authorization") api_token: String?): Call<GenericEntity<UserAuthData?>>

    @FormUrlEncoded
    @POST("reg-log-google")
    fun reLogSocialWithGoogle(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("device_token") device_token: String?,
        @Field("google_id") google_id: String?,
    ): Call<GenericEntity<AuthData?>>

    @FormUrlEncoded
    @POST("reg-log-facebook")
    fun reLogSocialWithFacebook(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("device_token") device_token: String?,
        @Field("facebook_id") facebookId: String?,
    ): Call<GenericEntity<AuthData?>>

    @FormUrlEncoded
    @POST("forget-password")
    fun recoverPasswordWithEmail(@Field("email") email: String?): Call<GenericEntity<PasswordRecoveryData?>>

    @FormUrlEncoded
    @POST("forget-password-code")
    fun onSuccessResetPassword(
        @Field("email") email: String?,
        @Field("code") code: String?,
        @Field("password") newPassword: String?,
    ): Call<GenericEntity<AuthData?>>

}

object AuthHandler {
    val singleton: IAuth by lazy {
        retrofit.create(IAuth::class.java)
    }
}