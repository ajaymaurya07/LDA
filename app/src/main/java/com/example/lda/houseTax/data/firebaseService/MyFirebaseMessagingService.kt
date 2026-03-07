package com.example.lda.houseTax.data.firebaseService

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.lda.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "Refreshed token: $token")
        // agar backend hai to yaha server ko bhejo
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Notification payload
        remoteMessage.notification?.let {
            val title = it.title ?: "Notification"
            val body = it.body ?: ""
            showNotification(title, body)
        }
    }

    private fun showNotification(title: String, message: String) {

        val builder = NotificationCompat.Builder(this, "fcm_channel")
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "fcm_channel",
            "FCM Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        manager.createNotificationChannel(channel)
        manager.notify(1, builder.build())
    }
}