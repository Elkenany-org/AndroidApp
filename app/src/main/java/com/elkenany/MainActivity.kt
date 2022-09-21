@file:Suppress("DEPRECATION")

package com.elkenany

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.elkenany.databinding.ActivityMainBinding
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        FacebookSdk.fullyInitialize()
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS)
        AppEventsLogger.activateApp(this.application)
        getToken()
    }

    companion object {
        fun getToken(): String? {
            var token: String? = null
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                token = if (!task.isSuccessful) {
                    Log.i("token", "failed to get token ${task.exception!!.message}")
                    null
                } else {
                    Log.i("token", task.result.toString())
                    task.result.toString()
                }
            }
            return token
        }
    }

}
