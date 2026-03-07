package com.example.lda.houseTax.notification.scheduler

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.lda.houseTax.notification.worker.BillAnalysisWorker
import java.util.concurrent.TimeUnit

object NotificationScheduler {

    fun schedule(context: Context) {

        val workRequest = PeriodicWorkRequestBuilder<BillAnalysisWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "bill_analysis",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )

    }
}

//        WorkManager.getInstance(context).enqueue(OneTimeWorkRequestBuilder<BillAnalysisWorker>().build()) // use for only testing