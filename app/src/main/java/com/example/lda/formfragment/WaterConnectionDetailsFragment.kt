package com.example.lda.formfragment

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWaterConnectionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerLaunchers()
        initImagePicker()
        setupSpinners()

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnSubmit.setOnClickListener {
            // Simulation of form submission
            Toast.makeText(requireContext(), "Application Submitted Successfully", Toast.LENGTH_LONG).show()
            requireActivity().finish()
        }
        
        setupUploadButtons()
    }

    private fun registerLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imagePickerHandler.handleCameraResult()
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imagePickerHandler.handleGalleryResult(it)
            }
        }
    }

    private fun initImagePicker() {
        imagePickerHandler = ImagePickerHandler(this) { index ->
            when (index) {
                1 -> {
                    binding.tvIdProofStatus.visibility = View.VISIBLE
                    binding.tvIdProofStatus
                }
                2 -> {
                    binding.tvPropertyDocStatus.visibility = View.VISIBLE
                    binding.tvPropertyDocStatus
                }
                3 -> {
                    binding.tvPhotoStatus.visibility = View.VISIBLE
                    binding.tvPhotoStatus
                }
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
        imagePickerHandler.setUpImageButtonClicks(
            binding.btnUploadIdProof to 1,
            binding.btnUploadPropertyDoc to 2,
            binding.btnUploadPhoto to 3
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
