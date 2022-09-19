package com.example.elkenany.api.firebase

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.elkenany.MainActivity
import com.example.elkenany.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "com.example.elkenany"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFireBaseService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification != null) {
            generateNotification(message.notification!!.title!!,
                message.notification!!.body!!,
                message.notification!!.eventTime!!)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("token", token)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun generateNotification(title: String, message: String, time: Long?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_logo_elkenany_01)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder)
    }

//    @SuppressLint("RemoteViewLayout")
//    private fun getRemoteView(title: String, message: String, time: Long?): RemoteViews {
//        val remoteView = RemoteViews(channelName, R.layout.push_notification_card)
//        remoteView.setTextViewText(R.id.push_notification_title, title)
//        remoteView.setTextViewText(R.id.push_notification_description, message)
//        remoteView.setTextViewText(R.id.push_sent_at, time.toString())
//        return remoteView
//    }
}