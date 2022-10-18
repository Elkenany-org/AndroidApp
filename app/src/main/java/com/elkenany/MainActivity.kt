@file:Suppress("DEPRECATION")

package com.elkenany

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.elkenany.databinding.ActivityMainBinding
import com.elkenany.utilities.GlobalLogicFunctions.Companion.getFCMToken
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsLogger

const val SHARED_PREFRENCES = "user_credentials"

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
        getFCMToken()
    }

}
