package com.elkenany.utilities

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

@Suppress("unused", "EnumEntryName")
enum class SharedPrefrencesType {
    local_stock,
    store,
    guide,
    news,
    shows,
    ships,
    magazine,
    jobs,
    tenders
}

class GlobalLogicFunctions {


    companion object {
        // get Firebase cloud messaging token
        suspend fun getFCMToken(): String {
            val token: String? = FirebaseMessaging.getInstance().token.await()
            return token.toString()
        }

        fun saveSharedPrefrences(
            activity: Activity?,
            pref: SharedPrefrencesType,
            sectionId: String?,
        ) {
            val sharedPrefrences =
                activity!!.getSharedPreferences(pref.toString(), Context.MODE_PRIVATE)
            with(sharedPrefrences.edit()) {
                putString("$pref", sectionId)
                apply()
            }
        }

        fun retrieveSavedSharedPrefrences(
            activity: Activity?,
            pref: SharedPrefrencesType,
        ): String? {
            val sharedPrefrences =
                activity!!.getSharedPreferences(pref.toString(), Context.MODE_PRIVATE)
            return sharedPrefrences.getString("$pref", null)
        }
    }
}