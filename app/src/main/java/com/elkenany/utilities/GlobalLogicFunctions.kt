package com.elkenany.utilities

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
            Log.i("token", token.toString())
            return token.toString()
        }

//        fun saveSharedPrefrences(
//            activity: Activity?,
//            pref: SharedPrefrencesType,
//            sectionId: String?,
//        ) {
//            val sharedPrefrences =
//                activity!!.getSharedPreferences(pref.toString(), Context.MODE_PRIVATE)
//            with(sharedPrefrences.edit()) {
//                putString("$pref", sectionId)
//                apply()
//            }
//            Log.i("pref", sectionId.toString())
//        }

//        fun retrieveSavedSharedPrefrences(
//            activity: Activity?,
//            pref: SharedPrefrencesType,
//        ): String? {
//            val sharedPrefrences =
//                activity!!.getSharedPreferences(pref.toString(), Context.MODE_PRIVATE)
//            Log.i("pref", sharedPrefrences.getString(pref.toString(), null).toString())
//            return sharedPrefrences.getString("$pref", null)
//        }
    }
}