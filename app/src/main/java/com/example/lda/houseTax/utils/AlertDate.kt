package com.example.lda.houseTax.utils

import android.graphics.Color
import com.example.lda.houseTax.data.PaymentAlert

object AlertDate {
    fun getPaymentMessage(inputDate: String?): PaymentAlert? {

        if (inputDate.isNullOrBlank()) return null

        val formattedDate = formatDate(inputDate)
        val status = getDateStatus(inputDate)

        return when (status) {

            "EXPIRED" -> PaymentAlert(
                title = "Payment Overdue",
                message = "House Tax payment was due on $formattedDate.Plz pay immediately to avoid penalty.",
                color = Color.parseColor("#D32F2F")
            )

            "TODAY" -> PaymentAlert(
                title = "Payment Due Today",
                message = "Your House Tax payment is due today ($formattedDate).Kindly complete it now.",
                color = Color.parseColor("#F57C00")
            )

            else -> PaymentAlert(
                title = "Upcoming Due Date",
                message = "The last date to pay House Tax is $formattedDate.Plz ensure timely payment.",
                color = Color.parseColor("#3F51B5")
            )
        }
    }

    private fun formatDate(inputDate: String?): String {
        val input = java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
        val output = java.text.SimpleDateFormat("dd MMMM yyyy", java.util.Locale.getDefault())
        val date = input.parse(inputDate!!)
        return output.format(date!!)
    }

    private fun getDateStatus(inputDate: String?): String {
        val sdf = java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
        val billDate = sdf.parse(inputDate!!)
        val today = sdf.parse(sdf.format(java.util.Date()))

        return when {
            billDate!!.after(today) -> "UPCOMING"
            billDate.before(today) -> "EXPIRED"
            else -> "TODAY"
        }
    }
}