@file:Suppress("DEPRECATION")

package com.example.elkenany

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.elkenany.databinding.ActivityMainBinding
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging

private const val APP_ID = "1:552649577410:android:5a94c06fc8a6c1ce00bc5b"
private const val WEB_API = "AIzaSyCuXH9l3JFLQvhItj62oghD7KeuLTwJdcs"
private const val PROJECT_ID = "causal-producer-359007"


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val options = FirebaseOptions.Builder()
            .setApplicationId(APP_ID) // Required for Analytics.
            .setProjectId(PROJECT_ID) // Required for Firebase Installations.
            .setApiKey(WEB_API) // Required for Auth.
            .build()
        FirebaseApp.initializeApp(this, options,"ElkenanyApk")
        Firebase.messaging.isAutoInitEnabled = true
//        FirebaseApp.initializeApp()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        FacebookSdk.fullyInitialize()
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS)
        AppEventsLogger.activateApp(this.application)
        getToken()
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i("token", "failed to get token ${task.exception!!.message}")
            } else {
                Log.i("token", task.result.toString())
            }
        }
    }
}
