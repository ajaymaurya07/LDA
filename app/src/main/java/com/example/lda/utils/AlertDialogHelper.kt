package com.example.lda.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class AlertDialogHelper {

    companion object {
        fun showAlertDialog(context: Context, title: String, message: String,
                            positiveButtonText: String, positiveButtonAction: (DialogInterface, Int) -> Unit,
                            negativeButtonText: String, negativeButtonAction: (DialogInterface, Int) -> Unit) {

            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setCancelable(false)

            builder.setPositiveButton(positiveButtonText, positiveButtonAction)
            builder.setNegativeButton(negativeButtonText, negativeButtonAction)

            builder.show()
        }
    }
}