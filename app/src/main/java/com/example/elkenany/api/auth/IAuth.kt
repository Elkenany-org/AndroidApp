package com.example.elkenany.api.auth

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.auth_data.AuthData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IAuth {

    //Login using the api service
    @FormUrlEncoded
    @POST("login")
    fun loginWithEmailAndPassword(
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
}

object AuthHandler {
    val singleton: IAuth by lazy {
        retrofit.create(IAuth::class.java)
    }
}