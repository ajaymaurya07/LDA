package com.example.lda.utils

import android.app.Activity
import android.app.AlertDialog
import android.widget.TextView
import com.example.lda.R

class LoderHelper(private val activity: Activity) {
    private var dialog: AlertDialog? = null

    fun startLoadingDialog(message: String) {
        if (dialog?.isShowing == true) {
            // Update message if already showing
            dialog?.findViewById<TextView>(R.id.progress_text)?.text = message
            return
        }

        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.loading, null)

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