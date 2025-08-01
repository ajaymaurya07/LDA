package com.example.lda.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


class ImagePickerHandler(
    private val fragment: Fragment,
    private val getTextViewForIndex: (Int) -> TextView
) {
    private var currentImageIndex = 1
    private var currentTextView: TextView? = null

    var photoUri1: Uri? = null
    var photoUri2: Uri? = null
    var photoUri3: Uri? = null

    private var photoUri: Uri? = null
    private var cameraPhotoFile: File? = null

    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryLauncher: ActivityResultLauncher<String>

    companion object {
        private const val AUTHORITY_SUFFIX = ".fileprovider"
    }

    fun setLaunchers(
        cameraLauncher: ActivityResultLauncher<Uri>,
        galleryLauncher: ActivityResultLauncher<String>
    ) {
        this.cameraLauncher = cameraLauncher
        this.galleryLauncher = galleryLauncher
    }

    fun setUpImageButtonClicks(vararg pairs: Pair<View, Int>) {
        pairs.forEach { (button, index) ->
            button.setOnClickListener {
                currentImageIndex = index
                currentTextView = getTextViewForIndex(index)
                showImagePickerDialog()
            }
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(fragment.requireContext())
            .setTitle("Choose Image Source")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openCamera() {
        val context = fragment.requireContext()
        cameraPhotoFile = File(
            context.getExternalFilesDir(null),
            "camera_photo.jpg"
        )
        photoUri = FileProvider.getUriForFile(
            context,
            context.packageName + AUTHORITY_SUFFIX,
            cameraPhotoFile!!
        )

        photoUri?.let { uri ->
            cameraLauncher.launch(uri)
        }
    }


    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    fun handleCameraResult() {
        photoUri?.let { handleSelectedUri(it) }
    }

    fun handleGalleryResult(uri: Uri) {
        photoUri = uri
        handleSelectedUri(uri)
    }

    private fun handleSelectedUri(uri: Uri) {
        val compressedFile = compressImage(uri)
        val compressedUri = Uri.fromFile(compressedFile)

        when (currentImageIndex) {
            1 -> photoUri1 = compressedUri
            2 -> photoUri2 = compressedUri
            3 -> photoUri3 = compressedUri
        }

        currentTextView?.text = compressedUri.nameOrPath()
    }

    private fun compressImage(uri: Uri): File {
        val inputStream = fragment.requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val compressedFile = File(
            fragment.requireContext().filesDir,
            "IMG_${System.currentTimeMillis()}.jpg"
        )
        val outputStream = FileOutputStream(compressedFile)

        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream)

        outputStream.flush()
        outputStream.close()

        return compressedFile
    }

    fun deleteCompressedImages() {
        listOf(photoUri1, photoUri2, photoUri3).forEach { uri ->
            uri?.path?.let { path ->
                val file = File(path)
                if (file.exists()) file.delete()
            }
        }

        photoUri1 = null
        photoUri2 = null
        photoUri3 = null
    }

    fun setUriNull() {
        photoUri1 = null
        photoUri2 = null
        photoUri3 = null
    }

    private fun Uri.nameOrPath(): String {
        return File(path ?: "").name
    }
}
