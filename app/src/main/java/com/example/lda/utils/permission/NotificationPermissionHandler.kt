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
        const val CAMERA_PERMISSION_CODE = 1003
    }

    /**
     * Starts the sequential permission check flow.
     * First checks for Notifications, then Camera.
     */
    fun checkPermissions() {
        checkNotificationPermission()
    }

    private fun checkNotificationPermission() {
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
            } else {
                // Already granted, move to next
                checkCameraPermission()
            }
        } else {
            // Not required for this version, move to next
            checkCameraPermission()
        }
    }

    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        }
    }

    fun handlePermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            NOTIFICATION_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission granted, proceed to camera
                    checkCameraPermission()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            activity,
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    ) {
                        showPermissionExplanation("Notification", "Allow notification permission to receive important updates.") {
                            checkNotificationPermission()
                        }
                    } else {
                        // Denied permanently or user doesn't want to grant, 
                        // we still might want to ask for camera if they just ignored this one.
                        // But usually, if they deny permanently, we show settings.
                        showGoToSettingsDialog("Notification")
                    }
                }
            }
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission granted
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity,
                            Manifest.permission.CAMERA
                        )
                    ) {
                        showPermissionExplanation("Camera", "Allow camera permission to capture photos for grievances.") {
                            checkCameraPermission()
                        }
                    } else {
                        showGoToSettingsDialog("Camera")
                    }
                }
            }
        }
    }

    private fun showPermissionExplanation(title: String, message: String, retryAction: () -> Unit) {
        AlertDialog.Builder(activity)
            .setTitle("$title Permission")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                retryAction()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                // If notification explanation is cancelled, still try camera
                if (title == "Notification") {
                    checkCameraPermission()
                }
            }
            .setCancelable(false)
            .show()
    }

    private fun showGoToSettingsDialog(permissionName: String) {
        AlertDialog.Builder(activity)
            .setTitle("Permission Required")
            .setMessage("$permissionName permission is required. Please enable it from App Settings.")
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)
            }
            .setNegativeButton("Skip") { dialog, _ ->
                dialog.dismiss()
                // If notification settings is skipped, still try camera
                if (permissionName == "Notification") {
                    checkCameraPermission()
                }
            }
            .setCancelable(false)
            .show()
    }
}
