package com.example.lda.eCourtUi


import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lda.houseTax.notification.scheduler.NotificationScheduler
import com.example.lda.houseTax.notification.util.NotificationUtil

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("TAG", "onCreate: application called")

        // Notification channel create
        NotificationUtil.createChannel(this)

        // WorkManager schedule
        NotificationScheduler.schedule(this)
    }
}
