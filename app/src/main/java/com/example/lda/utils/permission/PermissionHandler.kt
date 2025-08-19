package com.example.lda.utils.permission

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHandler(private val activity: Activity) {

    companion object {
        const val MULTIPLE_PERMISSION_CODE = 1001
    }

    // Function to check and request permissions
    fun checkPermissions() {
        val permissionsNeeded = arrayListOf<String>()

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // Request permissions if needed
        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionsNeeded.toTypedArray(), MULTIPLE_PERMISSION_CODE)
        } else {
            //Toast.makeText(activity, "All permissions already granted", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle the result of the permission request
    fun handlePermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MULTIPLE_PERMISSION_CODE) {
            val deniedPermissions = mutableListOf<String>()
            for (i in permissions.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permissions[i])
                }
            }

            if (deniedPermissions.isNotEmpty()) {
                handlePermissionsDenial(deniedPermissions)
            } else {
                //Toast.makeText(activity, "All permissions granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle denied permissions
    private fun handlePermissionsDenial(deniedPermissions: List<String>) {
        var showRationale = false

        // Check if "Don't ask again" was selected for any permission
        for (permission in deniedPermissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showRationale = true
            }
        }

        if (showRationale) {
            showPermissionExplanation()
        } else {
            showGoToSettingsDialog()
        }
    }

    // Show permission explanation
    private fun showPermissionExplanation() {
        AlertDialog.Builder(activity)
            .setTitle("Permissions Needed")
            .setMessage("This app requires Camera and Location permissions to function properly.")
            .setPositiveButton("OK") { _, _ ->
                checkPermissions() // Re-request permissions
            }
            .setCancelable(false)
            .create()
            .show()
    }

    // Show dialog to go to app settings if permissions are permanently denied
    private fun showGoToSettingsDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Permissions Required")
            .setMessage("You have denied some permissions. Allow all permissions in App Settings for the app to function properly.")
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)
            }
            .setCancelable(false)
            .create()
            .show()
    }
}