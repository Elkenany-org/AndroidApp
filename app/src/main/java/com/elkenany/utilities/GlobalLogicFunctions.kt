package com.elkenany.utilities

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

class GlobalLogicFunctions {
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
    }
}