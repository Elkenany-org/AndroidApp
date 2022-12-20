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
import com.facebook.appevents.AppEventsConstants.EVENT_NAME_ADDED_TO_CART
import com.facebook.appevents.AppEventsConstants.EVENT_PARAM_CONTENT_ID
import com.facebook.appevents.AppEventsConstants.EVENT_PARAM_CONTENT_TYPE
import com.facebook.appevents.AppEventsConstants.EVENT_PARAM_CURRENCY
import com.facebook.appevents.AppEventsLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val SHARED_PREFRENCES = "user_credentials"

class MainActivity : AppCompatActivity() {
    private lateinit var logger: AppEventsLogger
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        logger = AppEventsLogger.newLogger(this)
        FacebookSdk.apply {
            fullyInitialize()
            setAutoInitEnabled(true)
            addLoggingBehavior(LoggingBehavior.REQUESTS)
            setAutoLogAppEventsEnabled(true)
            setAdvertiserIDCollectionEnabled(true)
            setIsDebugEnabled(true)
            addLoggingBehavior(LoggingBehavior.APP_EVENTS)
        }

        val params = Bundle()
        params.putString(EVENT_PARAM_CURRENCY, "EGP")
        params.putString(EVENT_PARAM_CONTENT_TYPE, "fb_mobile_launch_source")
        params.putString(EVENT_PARAM_CONTENT_ID, "HDFU-8452")
        logger.logEvent(EVENT_NAME_ADDED_TO_CART,
            54.23,
            params)
        AppEventsLogger.activateApp(this.application)
        CoroutineScope(Dispatchers.IO).launch {
            getFCMToken()
        }
        logSentFriendRequestEvent()

    }

    private fun logSentFriendRequestEvent() {
        logger.logEvent("appInstall")
    }

}
