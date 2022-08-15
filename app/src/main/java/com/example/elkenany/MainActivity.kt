@file:Suppress("DEPRECATION")

package com.example.elkenany

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.elkenany.databinding.ActivityMainBinding
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        FacebookSdk.sdkInitialize(binding.root.context)
        AppEventsLogger.activateApp(this.application)


    }

}
