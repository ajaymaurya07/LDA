package com.example.lda.houseTax.notification.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lda.houseTax.data.database.AppDatabase
import com.example.lda.houseTax.notification.util.NotificationUtil
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class BillAnalysisWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val dao = AppDatabase.getDatabase(applicationContext).billDao()
        val bills = dao.getAllBills()

        if (bills.isNotEmpty()) {

            val bill = bills[0]

            if (bill.paymentDate == "-") {

                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

                val billDate = LocalDate.parse(bill.billDate, formatter)
                val today = LocalDate.now()

                val diff = ChronoUnit.DAYS.between(today, billDate)

                Log.d("TAG", "doWork: $diff")

                when (diff) {

                    7L -> notifyUser(
                        "Payment Reminder",
                        "Your house tax bill is due in 7 days (Due date: ${bill.billDate})."
                    )

                    3L -> notifyUser(
                        "Payment Reminder",
                        "Your house tax bill is due in 3 days (Due date: ${bill.billDate})."
                    )

                    1L -> notifyUser(
                        "Payment Reminder",
                        "Your house tax bill is due tomorrow (Due date: ${bill.billDate})."
                    )

                    0L -> notifyUser(
                        "Payment Due Today",
                        "Your house tax bill is due today. Please make the payment."
                    )

                    -1L -> notifyUser(
                        "Payment Overdue",
                        "Your house tax bill is overdue by 1 day. Please pay immediately."
                    )

                    -3L -> notifyUser(
                        "Payment Overdue",
                        "Your house tax bill is overdue by 3 days. Kindly clear it soon."
                    )

                    -7L -> notifyUser(
                        "Payment Overdue",
                        "Your house tax bill is overdue by 7 days. Immediate payment is required."
                    )
                }
            }
        }

        return Result.success()
    }

    private fun notifyUser(title: String, message: String) {

        NotificationUtil.showNotification(
            applicationContext,
            title,
            message
        )
    }
}