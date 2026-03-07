package com.example.lda.utils.permission

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class NotificationPermissionHandler(private val activity: Activity) {

    companion object {
        const val NOTIFICATION_PERMISSION_CODE = 1002
    }

    fun checkPermission() {

        // Notification permission only required for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            }
        }
    }

    fun handlePermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == NOTIFICATION_PERMISSION_CODE) {

            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {

                // Permission granted

            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                ) {
                    showPermissionExplanation()
                } else {
                    showGoToSettingsDialog()
                }
            }
        }
    }

    private fun showPermissionExplanation() {

        AlertDialog.Builder(activity)
            .setTitle("Notification Permission")
            .setMessage("Allow notification permission to receive important updates.")
            .setPositiveButton("OK") { _, _ ->
                checkPermission()
            }
            .setCancelable(false)
            .show()
    }

    private fun showGoToSettingsDialog() {

        AlertDialog.Builder(activity)
            .setTitle("Permission Required")
            .setMessage("Notification permission is required. Please enable it from App Settings.")
            .setPositiveButton("Go to Settings") { _, _ ->

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)

            }
            .setCancelable(false)
            .show()
    }
}