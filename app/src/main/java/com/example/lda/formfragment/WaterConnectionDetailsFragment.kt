package com.example.lda.formfragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.lda.databinding.FragmentWaterConnectionDetailsBinding
import com.example.lda.utils.ImagePickerHandler

class WaterConnectionDetailsFragment : Fragment() {
    private var _binding: FragmentWaterConnectionDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var imagePickerHandler: ImagePickerHandler
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
    
    private var docsCompletedCount = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWaterConnectionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerLaunchers()
        initImagePicker()
        setupSpinners()
        updateUI()

//        binding.btnBack.setOnClickListener {
//            requireActivity().onBackPressedDispatcher.onBackPressed()
//        }

        binding.btnSubmit.setOnClickListener {
            if (docsCompletedCount < 3) {
                Toast.makeText(requireContext(), "Please upload all required documents (100% completion required)", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Application Submitted Successfully", Toast.LENGTH_LONG).show()
                requireActivity().finish()
            }
        }
        
        setupUploadButtons()
    }

    private fun registerLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imagePickerHandler.handleCameraResult()
                onDocUploaded()
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imagePickerHandler.handleGalleryResult(it)
                onDocUploaded()
            }
        }
    }

    private fun onDocUploaded() {
        updateUI()
    }

    private fun updateUI() {
        var count = 0
        val uploadedColor = Color.parseColor("#ffbd18") // Green for uploaded
        val defaultBlue = Color.parseColor("#E67514")
        val gray = Color.parseColor("#E0E0E0")

        // 1. ID Proof
        if (imagePickerHandler.photoUri1 != null) {
            count++
            binding.btnIdCamera.backgroundTintList = ColorStateList.valueOf(uploadedColor)
            binding.btnIdBrowse.strokeColor = ColorStateList.valueOf(uploadedColor)
            binding.btnIdBrowse.setTextColor(uploadedColor)
            binding.btnIdBrowse.setIconTintResource(android.R.color.transparent) 
            binding.tvIdProofStatus.setTextColor(uploadedColor)
            binding.tvIdProofStatus.text = "ID Proof Uploaded"
        } else {
            binding.btnIdCamera.backgroundTintList = ColorStateList.valueOf(defaultBlue)
            binding.btnIdBrowse.strokeColor = ColorStateList.valueOf(gray)
            binding.btnIdBrowse.setTextColor(Color.parseColor("#1A1A1A"))
        }

        // 2. Property Document
        if (imagePickerHandler.photoUri2 != null) {
            count++
            binding.btnPropertyCamera.backgroundTintList = ColorStateList.valueOf(uploadedColor)
            binding.btnPropertyBrowse.strokeColor = ColorStateList.valueOf(uploadedColor)
            binding.btnPropertyBrowse.setTextColor(uploadedColor)
            binding.tvPropertyDocStatus.setTextColor(uploadedColor)
            binding.tvPropertyDocStatus.text = "Property Document Uploaded"
        } else {
            binding.btnPropertyCamera.backgroundTintList = ColorStateList.valueOf(defaultBlue)
            binding.btnPropertyBrowse.strokeColor = ColorStateList.valueOf(gray)
            binding.btnPropertyBrowse.setTextColor(Color.parseColor("#1A1A1A"))
        }

        // 3. Photo
        if (imagePickerHandler.photoUri3 != null) {
            count++
            binding.btnPhotoCamera.backgroundTintList = ColorStateList.valueOf(uploadedColor)
            binding.btnPhotoBrowse.strokeColor = ColorStateList.valueOf(uploadedColor)
            binding.btnPhotoBrowse.setTextColor(uploadedColor)
            binding.tvPhotoStatus.setTextColor(uploadedColor)
            binding.tvPhotoStatus.text = "Photo Uploaded"
        } else {
            binding.btnPhotoCamera.backgroundTintList = ColorStateList.valueOf(defaultBlue)
            binding.btnPhotoBrowse.strokeColor = ColorStateList.valueOf(gray)
            binding.btnPhotoBrowse.setTextColor(Color.parseColor("#1A1A1A"))
        }

        docsCompletedCount = count
        val percentage = (count * 100) / 3
        
        binding.btnSubmitProgress.progress = percentage
        
        if (count < 3) {
            binding.btnSubmit.text = "Upload Documents ($percentage%)"
            binding.btnSubmit.isEnabled = false
            binding.btnSubmit.setTextColor(defaultBlue)
        } else {
            binding.btnSubmit.text = "Submit Application"
            binding.btnSubmit.isEnabled = true
            binding.btnSubmit.setTextColor(Color.WHITE)
        }
    }

    private fun initImagePicker() {
        imagePickerHandler = ImagePickerHandler(this) { index ->
            when (index) {
                1 -> binding.tvIdProofStatus
                2 -> binding.tvPropertyDocStatus
                3 -> binding.tvPhotoStatus
                else -> binding.tvIdProofStatus
            }
        }
        imagePickerHandler.setLaunchers(cameraLauncher, galleryLauncher)
    }

    private fun setupSpinners() {
        val connectionTypes = arrayOf("Water", "Sewerage", "Both")
        binding.spinnerConnectionType.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, connectionTypes))

        val requirements = arrayOf("New Connection", "Reconnection", "Modification")
        binding.spinnerRequirement.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, requirements))

        val categories = arrayOf("Residential", "Commercial", "Industrial")
        binding.spinnerCategory.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories))

        val pipeSizes = arrayOf("1/2 inch", "3/4 inch", "1 inch", "1.5 inch", "2 inch")
        binding.spinnerPipeSize.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, pipeSizes))

        val idProofTypes = arrayOf("Voter ID", "Driving Licence", "PAN Card", "Passport")
        binding.spinnerIdProofType.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, idProofTypes))

        val propertyDocTypes = arrayOf("Lease Agreement", "Rent Agreement", "Registry")
        binding.spinnerPropertyDocType.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, propertyDocTypes))
    }

    private fun setupUploadButtons() {
        // ID Proof
        binding.btnIdCamera.setOnClickListener {
            openDirectCamera(1)
        }
        binding.btnIdBrowse.setOnClickListener {
            openDirectGallery(1)
        }

        // Property Doc
        binding.btnPropertyCamera.setOnClickListener {
            openDirectCamera(2)
        }
        binding.btnPropertyBrowse.setOnClickListener {
            openDirectGallery(2)
        }

        // Photo
        binding.btnPhotoCamera.setOnClickListener {
            openDirectCamera(3)
        }
        binding.btnPhotoBrowse.setOnClickListener {
            openDirectGallery(3)
        }
    }
    
    private fun openDirectCamera(index: Int) {
        setHandlerState(index)
        val method = imagePickerHandler.javaClass.getDeclaredMethod("openCamera")
        method.isAccessible = true
        method.invoke(imagePickerHandler)
    }

    private fun openDirectGallery(index: Int) {
        setHandlerState(index)
        val method = imagePickerHandler.javaClass.getDeclaredMethod("openGallery")
        method.isAccessible = true
        method.invoke(imagePickerHandler)
    }
    
    private fun setHandlerState(index: Int) {
        val fieldIndex = imagePickerHandler.javaClass.getDeclaredField("currentImageIndex")
        fieldIndex.isAccessible = true
        fieldIndex.set(imagePickerHandler, index)
        
        val fieldTv = imagePickerHandler.javaClass.getDeclaredField("currentTextView")
        fieldTv.isAccessible = true
        fieldTv.set(imagePickerHandler, when(index) {
            1 -> binding.tvIdProofStatus
            2 -> binding.tvPropertyDocStatus
            3 -> binding.tvPhotoStatus
            else -> null
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
