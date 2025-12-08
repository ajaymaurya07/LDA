package com.example.lda.eCourtUi.utils

import android.app.Activity
import android.app.AlertDialog
import android.widget.TextView
import com.example.lda.R


class ProgressBar(private val activity: Activity) {

    private var dialog: AlertDialog? = null

    fun startLoadingDialog(message: String) {
        // Prevent multiple dialogs
        if (dialog?.isShowing == true) return

        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.activity_loading, null)

        val loadingMessageTextView: TextView = view.findViewById(R.id.progress_text)
        loadingMessageTextView.text = message

        builder.setView(view)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    fun dismissDialog() {
        if (dialog?.isShowing == true && !activity.isFinishing && !activity.isDestroyed) {
            dialog?.dismiss()
        }
        dialog = null
    }
}
