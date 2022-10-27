package com.elkenany.utilities

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class GlobalLogicFunctions {
    companion object {
        // get Firebase cloud messaging token
        suspend fun getFCMToken(): String {
            val token: String? = FirebaseMessaging.getInstance().token.await()
            Log.i("tokennnnnnnnnnnnnnnnnnn", token.toString())
            return token.toString()
        }
    }
}