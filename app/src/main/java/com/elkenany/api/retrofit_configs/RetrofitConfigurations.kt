package com.elkenany.api.retrofit_configs

import android.util.Log
import com.elkenany.entities.GenericEntity
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.SocketTimeoutException

////this base url for published server
//const val BASE_URL = "https://admin.elkenany.com/api/"

//this base url for testing server
const val BASE_URL = "https://test.elkenany.com/api/"

private val moshi by lazy {
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

private val intercepter by lazy {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    OkHttpClient.Builder().addInterceptor(interceptor).build()
}

val retrofit: Retrofit by lazy {
    Retrofit.Builder()
//        .client(intercepter)//Make sure to comment this section before publshing any versions
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

// Generic function to handle all Api Request exceptions
suspend fun <T> onHandelingResponseStates(
    funName: String,
    request: () -> Call<GenericEntity<T>>,
): GenericEntity<T> {
    return try {
        request().await()
    } catch (e: HttpException) {
        Log.i(funName, e.message.toString())
        GenericEntity(null, e.code().toString(), null)
    } catch (e: SocketTimeoutException) {
        Log.i(funName, e.message.toString())
        GenericEntity(null, "408", null) // 408 means request timed out
    } catch (e: Exception) {
        Log.i(funName, e.message.toString())
        GenericEntity(null, "500", null)
    }
}