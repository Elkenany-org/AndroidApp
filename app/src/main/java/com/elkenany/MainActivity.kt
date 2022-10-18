@file:Suppress("DEPRECATION")

package com.elkenany

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.elkenany.databinding.ActivityMainBinding
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.messaging.FirebaseMessaging

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

    companion object {
        // get Firebase cloud messaging token
        fun getFCMToken(): String? {
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

        // send mail to specific email
        fun emailThisEmail(email: String?, activity: Activity) {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            emailIntent.type = "message/rfc822"
            activity.startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"))
        }

        // make a phone call to specific number
        fun callThisNumber(phone: String?, context: Context, activity: Activity) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phone")
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.CALL_PHONE), 1
                )
            } else {
                activity.startActivity(callIntent)
            }

        }

        // navigate to gps to locate a specific location
        fun locateThisLocation(latid: String, longtid: String, activity: Activity) {
            val gmmIntentUri = Uri.parse("google.navigation:q=${latid},${longtid}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            activity.startActivity(mapIntent)
        }
    }

}
