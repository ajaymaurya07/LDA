package com.example.lda.houseTax.grivance

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lda.BaseActivity
import com.example.lda.R
import com.example.lda.databinding.ActivityTrackGrivanceBinding
import com.example.lda.databinding.ItemTrackInfoBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.model.GrievanceStatusData
import com.example.lda.utils.LoderHelper

class TrackGrivanceActivity : BaseActivity() {
    private lateinit var binding: ActivityTrackGrivanceBinding
    private lateinit var loaderHelper: LoderHelper
    private lateinit var viewModel: PaymentViewModel
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackGrivanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        loaderHelper = LoderHelper(this)
        preferenceManager = PreferenceManager(this)
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]

        setupToolbar()
        observeViewModel()
        
        val grievanceNo = intent.getStringExtra("grievanceNo")
        if (grievanceNo != null) {
            fetchGrievanceStatus(grievanceNo)
        } else {
            Toast.makeText(this, "Grievance number not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupToolbar() {
        applySafeAreaInsets(
            rootView = binding.root,
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )
        binding.navBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                loaderHelper.startLoadingDialog("Fetching status...")
            } else {
                loaderHelper.dismissDialog()
            }
        }

        viewModel.grievanceStatus.observe(this) { response ->
            if (response != null) {
                if (response.success) {
                    val dataList = response.data
                    if (!dataList.isNullOrEmpty()) {
                        showContent(true)
                        updateUI(dataList[0])
                    } else {
                        showContent(false)
                    }
                } else {
                    showContent(false)
                    Toast.makeText(this, "Failed: ${response.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchGrievanceStatus(grievanceNo: String) {
        viewModel.fetchGrievanceStatus(grievanceNo, this, preferenceManager)
    }

    private fun showContent(found: Boolean) {
        if (found) {
            binding.mainContent.visibility = View.VISIBLE
            binding.noStatusLayout.visibility = View.GONE
        } else {
            binding.mainContent.visibility = View.GONE
            binding.noStatusLayout.visibility = View.VISIBLE
        }
    }

    private fun updateUI(data: GrievanceStatusData) {
        binding.apply {
            tvComplaintId.text = data.complaintId
            tvCategory.text = data.categoryName
            tvSubCategory.text = data.subCategoryName
            tvDueDate.text = data.dueDate
            tvComplaintDesc.text = data.complaintDesc
            tvDate.text = data.complaintDate
            tvTime.text = ", ${data.complaintTime}"
            tvLandmark.text = data.landmark

            // Location Info
            setupTrackInfo(layoutUlb, "ULB Name", data.ulbName, R.drawable.logo)
            setupTrackInfo(layoutZoneWard, "Zone / Ward", "${data.zoneName} / ${data.wardName}", R.drawable.gps)
            setupTrackInfo(layoutMohalla, "Mohalla", data.mohallaName, R.drawable.gps)
            setupTrackInfo(layoutAddress, "Address", "${data.address1 ?: ""}\n${data.address2 ?: ""}".trim(), R.drawable.gps)

            // Contact Info
            setupTrackInfo(layoutName, "Name", data.name, R.drawable.profile_icon)
            setupTrackInfo(layoutFather, "Father/Husband Name", data.fatherHusbandName, R.drawable.profile_icon)
            setupTrackInfo(layoutMobile, "Mobile", data.mobile, R.drawable.phone)
            setupTrackInfo(layoutEmail, "Email", data.email, R.drawable.baseline_email_24)

            // Authorities
            tvOffName.text = data.assignedOffName ?: "N/A"
            tvOffDetails.text = "${data.assignedOffPost ?: ""} • ${data.assignedOffMobile ?: ""}"
            tvEmpName.text = data.assignedEmpName ?: "N/A"
            tvEmpDetails.text = "${data.assignedEmpPost ?: ""} • ${data.assignedEmpMobile ?: ""}"

            btnCallOff.setOnClickListener { makeCall(data.assignedOffMobile) }
            btnCallEmp.setOnClickListener { makeCall(data.assignedEmpMobile) }

            // Resolution
            setupTrackInfo(layoutCloseDate, "Close Date", data.closeDate ?: "-", R.drawable.clock)
            setupTrackInfo(layoutCloseRemark, "Close Remark", data.closeRemark ?: "-", R.drawable.profile)
        }
    }

    private fun setupTrackInfo(layoutBinding: ItemTrackInfoBinding, label: String, value: String?, iconRes: Int) {
        layoutBinding.tvLabel.text = label
        layoutBinding.tvValue.text = value ?: "N/A"
        layoutBinding.ivIcon.setImageResource(iconRes)
    }

    private fun makeCall(phoneNumber: String?) {
        if (!phoneNumber.isNullOrEmpty() && phoneNumber != "N/A") {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            startActivity(intent)
        } else {
            Toast.makeText(this, "Phone number not available", Toast.LENGTH_SHORT).show()
        }
    }
}
