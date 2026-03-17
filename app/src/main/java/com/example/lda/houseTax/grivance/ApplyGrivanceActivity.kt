package com.example.lda.houseTax.grivance

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.databinding.ActivityApplyGrivanceBinding
import com.example.lda.databinding.DialogSearchableSelectionBinding
import com.example.lda.databinding.LayoutPhotoPickerBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.data.database.AppDatabase
import com.example.lda.houseTax.data.database.entity.PropertyEntity
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.houseTax.viewmodel.SharedViewModel
import com.example.lda.model.DataItem
import com.example.lda.model.MohallaItem
import com.example.lda.model.SubCategoriesItem
import com.example.lda.model.UlbItem
import com.example.lda.model.WardItem
import com.example.lda.model.ZoneItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class ApplyGrivanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyGrivanceBinding
    private lateinit var viewModel: PaymentViewModel
    private lateinit var sharedViewModel: SharedViewModel
    
    private var ulbList: List<UlbItem> = emptyList()
    private var zoneList: List<ZoneItem> = emptyList()
    private var wardList: List<WardItem> = emptyList()
    private var mohallaList: List<MohallaItem> = emptyList()
    private var categoryList: List<DataItem> = emptyList()
    private var subCategoryList: List<SubCategoriesItem> = emptyList()
    private var propertyList: List<PropertyEntity> = emptyList()

    private var selectedUlbItem: UlbItem? = null
    private var selectedZoneItem: ZoneItem? = null
    private var selectedWardItem: WardItem? = null
    private var selectedMohallaItem: MohallaItem? = null
    private var selectedCategory: DataItem? = null
    private var selectedSubCategory: SubCategoriesItem? = null

    private var imageUri: Uri? = null
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyGrivanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        applySafeAreaInsets(
            rootView = binding.root,
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        setupToolbar()
        observeData()
        setupLaunchers()
        setupListeners()
        fetchProperties()

        viewModel.fetchGrievanceData()
        sharedViewModel.ulbData("")
    }

    private fun fetchProperties() {
        lifecycleScope.launch {
            try {
                val db = AppDatabase.getDatabase(this@ApplyGrivanceActivity)
                propertyList = db.propertyDao().getAllProperties()
            } catch (e: Exception) {
                Log.e("ApplyGrivance", "Error fetching properties", e)
            }
        }
    }

    private fun setupToolbar() {
        binding.navBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun observeData() {
        viewModel.grievanceData.observe(this) { response ->
            if (response?.success == true) {
                categoryList = response.data?.filterNotNull() ?: emptyList()
            }
        }

        sharedViewModel.ulbList.observe(this) { list -> ulbList = list }
        sharedViewModel.zoneList.observe(this) { list -> zoneList = list }
        sharedViewModel.wardList.observe(this) { list -> wardList = list }
        sharedViewModel.mohallaList.observe(this) { list -> mohallaList = list }
    }

    private fun setupListeners() {
        binding.spinnerProperty.setOnClickListener {
            if (propertyList.isEmpty()) {
                Toast.makeText(this, "No registered properties found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showSearchableDialog("Select Property", propertyList, { "${it.ownerName} (${it.propertyId})" }) { property ->
                binding.spinnerProperty.setText("${property.ownerName} (${property.propertyId})", false)
                binding.etName.setText(property.ownerName)
                binding.etMobileNumber.setText(property.phoneNumber)
            }
        }

        binding.spinnerUlb.setOnClickListener {
            if (ulbList.isEmpty()) {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
                sharedViewModel.ulbData("")
                return@setOnClickListener
            }
            showSearchableDialog("Select ULB", ulbList, { "${it.ulbName} (${it.ulbType})" }) { item ->
                selectedUlbItem = item
                binding.spinnerUlb.setText("${item.ulbName} (${item.ulbType})", false)
                resetLocationFields(1)
                item.ulbId?.let { sharedViewModel.zoneData(it) }
            }
        }

        binding.spinnerZone.setOnClickListener {
            if (selectedUlbItem == null) {
                Toast.makeText(this, "Please select ULB first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (zoneList.isEmpty()) {
                Toast.makeText(this, "No Zones found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showSearchableDialog("Select Zone", zoneList, { it.zoneName ?: "" }) { item ->
                selectedZoneItem = item
                binding.spinnerZone.setText(item.zoneName, false)
                resetLocationFields(2)
                selectedUlbItem?.ulbId?.let { ulbId ->
                    item.zoneId?.let { zoneId -> sharedViewModel.wardData(ulbId, zoneId) }
                }
            }
        }

        binding.spinnerWard.setOnClickListener {
            if (selectedZoneItem == null) {
                Toast.makeText(this, "Please select Zone first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (wardList.isEmpty()) {
                Toast.makeText(this, "No Wards found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showSearchableDialog("Select Ward", wardList, { it.wardName ?: "" }) { item ->
                selectedWardItem = item
                binding.spinnerWard.setText(item.wardName, false)
                resetLocationFields(3)
                val ulbId = selectedUlbItem?.ulbId
                val zoneId = selectedZoneItem?.zoneId
                if (ulbId != null && zoneId != null && item.wardId != null) {
                    sharedViewModel.mohallaData(ulbId, zoneId, item.wardId)
                }
            }
        }

        binding.spinnerMohalla.setOnClickListener {
            if (selectedWardItem == null) {
                Toast.makeText(this, "Please select Ward first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (mohallaList.isEmpty()) {
                Toast.makeText(this, "No Mohalla found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showSearchableDialog("Select Mohalla", mohallaList, { it.mohallaName ?: "" }) { item ->
                selectedMohallaItem = item
                binding.spinnerMohalla.setText(item.mohallaName, false)
            }
        }

        binding.spinnerCategory.setOnClickListener {
            if (categoryList.isEmpty()) {
                Toast.makeText(this, "Loading categories...", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showSearchableDialog("Select Category", categoryList, { it.serviceName ?: "" }) { item ->
                selectedCategory = item
                binding.spinnerCategory.setText(item.serviceName, false)
                selectedSubCategory = null
                binding.spinnerSubCategory.setText("", false)
                
                subCategoryList = item.subCategories?.filterNotNull() ?: emptyList()
                if (subCategoryList.isNotEmpty()) {
                    binding.layoutSubCategory.visibility = View.VISIBLE
                } else {
                    binding.layoutSubCategory.visibility = View.GONE
                }
            }
        }

        binding.spinnerSubCategory.setOnClickListener {
            if (selectedCategory == null) {
                Toast.makeText(this, "Please select Category first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (subCategoryList.isEmpty()) {
                Toast.makeText(this, "No sub-categories found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showSearchableDialog("Select Sub-Category", subCategoryList, { it.subName ?: "" }) { item ->
                selectedSubCategory = item
                binding.spinnerSubCategory.setText(item.subName, false)
            }
        }

        binding.btnUploadPhoto.setOnClickListener { showPhotoPickerOptions() }
        binding.btnSubmit.setOnClickListener { validateAndSubmit() }
        
        binding.btnRemovePhoto.setOnClickListener {
            imageUri = null
            binding.ivSelectedPhoto.setImageURI(null)
            binding.imagePreviewCard.visibility = View.GONE
            binding.btnPreviewPhoto.visibility = View.GONE
            binding.tvUploadHint.text = "No photo added"
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
    }

    private fun <T> showSearchableDialog(
        title: String,
        list: List<T>,
        displayNameMapper: (T) -> String,
        onItemSelected: (T) -> Unit
    ) {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val dialogBinding = DialogSearchableSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        
        dialogBinding.tvDialogTitle.text = title
        
        val adapter = GenericSelectionAdapter(list, displayNameMapper) { item ->
            onItemSelected(item)
            dialog.dismiss()
        }
        
        dialogBinding.rvItems.adapter = adapter
        
        dialogBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        
        dialogBinding.btnCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun resetLocationFields(level: Int) {
        if (level <= 1) {
            selectedZoneItem = null
            binding.spinnerZone.setText("", false)
            sharedViewModel.clearZoneList()
        }
        if (level <= 2) {
            selectedWardItem = null
            binding.spinnerWard.setText("", false)
            sharedViewModel.clearWardList()
        }
        if (level <= 3) {
            selectedMohallaItem = null
            binding.spinnerMohalla.setText("", false)
            sharedViewModel.clearMohallaList()
        }
    }

    private fun validateAndSubmit() {
        val name = binding.etName.text.toString().trim()
        val mobile = binding.etMobileNumber.text.toString().trim()
        val desc = binding.etDescription.text.toString().trim()

        if (name.isEmpty()) { binding.etName.error = "Enter Name"; return }
        if (mobile.length != 10) { binding.etMobileNumber.error = "Enter 10 digit number"; return }
        if (selectedUlbItem == null) { Toast.makeText(this, "Select ULB", Toast.LENGTH_SHORT).show(); return }
        if (selectedCategory == null) { Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show(); return }
        if (desc.isEmpty()) { binding.etDescription.error = "Enter details"; return }

        Log.d("GrievanceSubmit", "Name: $name, ULB: ${selectedUlbItem?.ulbName}, Category ID: ${selectedCategory?.serviceCode}")
        Toast.makeText(this, "Grievance submitted successfully!", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun setupLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) handleImageSelected(imageUri)
        }
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { imageUri = it; handleImageSelected(it) }
        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.CAMERA] == true) openCamera()
            else Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleImageSelected(uri: Uri?) {
        uri?.let {
            binding.ivSelectedPhoto.setImageURI(it)
            binding.imagePreviewCard.visibility = View.VISIBLE
            binding.btnPreviewPhoto.visibility = View.VISIBLE
            binding.btnPreviewPhoto.text = "Hide Preview"
            binding.tvUploadHint.text = "Photo added successfully"
        }
    }

    private fun openCamera() {
        val values = ContentValues().apply { put(MediaStore.Images.Media.TITLE, "New Picture") }
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        imageUri?.let { cameraLauncher.launch(it) }
    }

    private fun showPhotoPickerOptions() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val dialogBinding = LayoutPhotoPickerBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.layoutCapture.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) openCamera()
            else permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
            dialog.dismiss()
        }
        dialogBinding.layoutGallery.setOnClickListener { galleryLauncher.launch("image/*"); dialog.dismiss() }
        dialogBinding.btnCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private class GenericSelectionAdapter<T>(
        private val originalList: List<T>,
        private val displayNameMapper: (T) -> String,
        private val onItemClick: (T) -> Unit
    ) : RecyclerView.Adapter<GenericSelectionAdapter.ViewHolder>() {
        
        private var filteredList = originalList.toList()

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(R.id.tvItemName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_selectable_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = filteredList[position]
            holder.tvName.text = displayNameMapper(item)
            holder.itemView.setOnClickListener { onItemClick(item) }
        }

        override fun getItemCount() = filteredList.size

        fun filter(query: String) {
            filteredList = if (query.isEmpty()) {
                originalList
            } else {
                originalList.filter { 
                    displayNameMapper(it).lowercase().contains(query.lowercase()) 
                }
            }
            notifyDataSetChanged()
        }
    }
}
