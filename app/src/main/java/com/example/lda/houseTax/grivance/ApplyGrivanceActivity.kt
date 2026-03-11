package com.example.lda.houseTax.grivance

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.ActivityApplyGrivanceBinding
import com.example.lda.databinding.LayoutPhotoPickerBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.model.DataItem
import com.example.lda.model.SubCategoriesItem
import com.google.android.material.bottomsheet.BottomSheetDialog

class ApplyGrivanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyGrivanceBinding
    private lateinit var viewModel: PaymentViewModel
    private var grievanceList: List<DataItem?>? = null
    private var imageUri: Uri? = null

    private var selectedServiceCode: Int? = null
    private var selectedSubCatCode: Int? = null

    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyGrivanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]


        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        setupToolbar()
        observer()
        setupLaunchers()
        setupListeners()

        viewModel.fetchGrievanceData()
    }

    private fun setupLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                handleImageSelected(imageUri)
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri = it
                handleImageSelected(it)
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
            if (cameraGranted) {
                openCamera()
            } else {
                Toast.makeText(this, "Camera permission required to take photo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleImageSelected(uri: Uri?) {
        uri?.let {
            binding.ivSelectedPhoto.setImageURI(it)
            binding.tvUploadHint.visibility = View.VISIBLE
            binding.tvUploadHint.text = "Photo added successfully"
            binding.tvUploadHint.setTextColor(ContextCompat.getColor(this, R.color.primary))
            binding.btnPreviewPhoto.visibility = View.VISIBLE
            binding.imagePreviewCard.visibility = View.GONE
            binding.btnPreviewPhoto.text = "Preview Photo"
            Toast.makeText(this, "Photo added successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observer() {
        viewModel.grievanceData.observe(this) { response ->
            if (response?.success == true) {
                grievanceList = response.data
                setupCategorySpinner()
            } else {
                Toast.makeText(this, response?.message ?: "Failed to fetch categories", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupCategorySpinner() {
        val categoryNames = grievanceList?.mapNotNull { it?.serviceName } ?: emptyList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categoryNames)
        binding.spinnerCategory.setAdapter(adapter)

        binding.spinnerCategory.setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = grievanceList?.get(position)
            selectedServiceCode = selectedCategory?.serviceCode
            selectedSubCatCode = null // Reset subcategory when category changes

            selectedCategory?.subCategories?.let { subCategories ->
                setupSubCategorySpinner(subCategories)
                binding.layoutSubCategory.visibility = View.VISIBLE
                binding.spinnerSubCategory.setText("", false)
            }
        }
    }

    private fun setupSubCategorySpinner(subCategories: List<SubCategoriesItem?>) {
        val subCategoryNames = subCategories.mapNotNull { it?.subName }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, subCategoryNames)
        binding.spinnerSubCategory.setAdapter(adapter)

        binding.spinnerSubCategory.setOnItemClickListener { _, _, position, _ ->
            selectedSubCatCode = subCategories[position]?.subCatCode
        }
    }

    private fun setupListeners() {
        binding.btnUploadPhoto.setOnClickListener {
            showPhotoPickerOptions()
        }

        binding.btnPreviewPhoto.setOnClickListener {
            if (binding.imagePreviewCard.visibility == View.VISIBLE) {
                binding.imagePreviewCard.visibility = View.GONE
                binding.btnPreviewPhoto.text = "Preview Photo"
            } else {
                binding.imagePreviewCard.visibility = View.VISIBLE
                binding.btnPreviewPhoto.text = "Hide Preview"
            }
        }

        binding.btnRemovePhoto.setOnClickListener {
            imageUri = null
            binding.ivSelectedPhoto.setImageURI(null)
            binding.imagePreviewCard.visibility = View.GONE
            binding.btnPreviewPhoto.visibility = View.GONE
            binding.tvUploadHint.visibility = View.VISIBLE
            binding.tvUploadHint.text = "No photo added"
            binding.tvUploadHint.setTextColor(ContextCompat.getColor(this, R.color.gray))
        }

        binding.btnSubmit.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun showPhotoPickerOptions() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val dialogBinding = LayoutPhotoPickerBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.layoutCapture.setOnClickListener {
            if (checkCameraPermission()) {
                openCamera()
                dialog.dismiss()
            } else {
                requestCameraPermission()
                dialog.dismiss()
            }
        }

        dialogBinding.layoutGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
            dialog.dismiss()
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        imageUri?.let {
            cameraLauncher.launch(it)
        } ?: run {
            Toast.makeText(this, "Failed to create image file", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateAndSubmit() {
        val name = binding.etName.text.toString().trim()
        val fatherName = binding.etFatherName.text.toString().trim()
        val mobile = binding.etMobileNumber.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val ulb = binding.spinnerUlb.text.toString().trim()
        val zone = binding.spinnerZone.text.toString().trim()
        val ward = binding.spinnerWard.text.toString().trim()
        val mohalla = binding.spinnerMohalla.text.toString().trim()
        val landmark = binding.etLandmark.text.toString().trim()
        val category = binding.spinnerCategory.text.toString().trim()
        val subCategory = binding.spinnerSubCategory.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()

        if (name.isEmpty()) { binding.etName.error = "Enter Name"; return }
        if (fatherName.isEmpty()) { binding.etFatherName.error = "Enter Father Name"; return }
        if (mobile.length != 10) { binding.etMobileNumber.error = "Enter 10 digit mobile number"; return }
        if (email.isEmpty()) { binding.etEmail.error = "Enter Email"; return }
        if (address.isEmpty()) { binding.etAddress.error = "Enter Address"; return }
        if (ulb.isEmpty()) { Toast.makeText(this, "Select ULB", Toast.LENGTH_SHORT).show(); return }
        if (zone.isEmpty()) { Toast.makeText(this, "Select Zone", Toast.LENGTH_SHORT).show(); return }
        if (ward.isEmpty()) { Toast.makeText(this, "Select Ward", Toast.LENGTH_SHORT).show(); return }
        if (mohalla.isEmpty()) { Toast.makeText(this, "Select Mohalla", Toast.LENGTH_SHORT).show(); return }
        if (category.isEmpty()) { Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show(); return }
        if (subCategory.isEmpty()) { Toast.makeText(this, "Select Sub-Category", Toast.LENGTH_SHORT).show(); return }
        if (description.isEmpty()) { binding.etDescription.error = "Enter description"; return }

        // Log all data as requested
        Log.d("GrievanceSubmit", "--- Personal Info ---")
        Log.d("GrievanceSubmit", "Name: $name")
        Log.d("GrievanceSubmit", "Father Name: $fatherName")
        Log.d("GrievanceSubmit", "Mobile: $mobile")
        Log.d("GrievanceSubmit", "Email: $email")
        Log.d("GrievanceSubmit", "Address: $address")

        Log.d("GrievanceSubmit", "--- Location Info ---")
        Log.d("GrievanceSubmit", "ULB: $ulb")
        Log.d("GrievanceSubmit", "Zone: $zone")
        Log.d("GrievanceSubmit", "Ward: $ward")
        Log.d("GrievanceSubmit", "Mohalla: $mohalla")
        Log.d("GrievanceSubmit", "Landmark: $landmark")

        Log.d("GrievanceSubmit", "--- Grievance Info ---")
        Log.d("GrievanceSubmit", "Service Code: $selectedServiceCode")
        Log.d("GrievanceSubmit", "Category Name: $category")
        Log.d("GrievanceSubmit", "Sub-Category Code: $selectedSubCatCode")
        Log.d("GrievanceSubmit", "Sub-Category Name: $subCategory")
        Log.d("GrievanceSubmit", "Description: $description")
        Log.d("GrievanceSubmit", "Image URI: ${imageUri ?: "No image"}")

        Toast.makeText(this, "Grievance submitted successfully!", Toast.LENGTH_LONG).show()
        finish()
    }
}
