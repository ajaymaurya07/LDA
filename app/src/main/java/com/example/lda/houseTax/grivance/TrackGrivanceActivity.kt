package com.example.lda.houseTax.grivance

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lda.R
import com.example.lda.databinding.ActivityTrackGrivanceBinding
import com.example.lda.databinding.ItemTrackInfoBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.model.GrievanceStatusData

class TrackGrivanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrackGrivanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackGrivanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = binding.root,
            statusBarColor = ContextCompat.getColor(this, R.color.primary),
            lightStatusBar = true,
        )

        setupToolbar()
        
        // Mock data for UI demonstration based on provided JSON
        val mockData = GrievanceStatusData(
            ulbName = "Nagar Nigam Lucknow",
            mohallaName = "KALYANPUR WEST",
            zoneName = "Zone-3",
            wardName = "SHANKAR PURWA-2",
            complaintId = "PGF43925260008472",
            complaintDate = "20-02-2026",
            categoryName = "Garbage and Cleanliness",
            subCategoryName = "Lifting of Malwa",
            landmark = "Near Post Office",
            complaintDesc = "Water supply issue",
            name = "Prasoon Mishra",
            fatherHusbandName = "p K Mishra",
            mobile = "9999933210", // Updated to a dialable number
            email = "test@gmail.com",
            address1 = "Civil Lines Lucknow",
            address2 = "Near Post Office",
            assignedEmpName = "Amit Singh",
            assignedEmpMobile = "9999988888",
            assignedEmpPost = "Ast SWO",
            assignedOffName = "Rekha Singh",
            assignedOffMobile = "7777766666",
            assignedOffPost = "SWO",
            status = "New application",
            closeDate = "-",
            closeRemark = "Processing",
            complaintTime = "12:51 P.M.",
            closeTime = "12:00 A.M.",
            reComplain = 0,
            dueDate = "22-02-2026"
        )

        bindData(mockData)
        
        binding.btnCallEmp.setOnClickListener { makeCall(mockData.assignedEmpMobile) }
        binding.btnCallOff.setOnClickListener { makeCall(mockData.assignedOffMobile) }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun bindData(data: GrievanceStatusData) {
        // Status Section
//        binding.tvStatusBadge.text = data.status?.uppercase() ?: "N/A"
        binding.tvComplaintId.text = data.complaintId
        binding.tvCategory.text = data.categoryName
        binding.tvSubCategory.text = data.subCategoryName
        binding.tvComplaintDesc.text = data.complaintDesc
        binding.tvDate.text = data.complaintDate
        binding.tvTime.text = data.complaintTime
        binding.tvLandmark.text = data.landmark
        binding.tvDueDate.text = data.dueDate

        // Location Details Section
        setupInfoItem(binding.layoutUlb, R.drawable.e_nagar_seva_logo, "ULB", data.ulbName)
        setupInfoItem(binding.layoutZoneWard, R.drawable.cases, "ZONE / WARD", "${data.zoneName} • ${data.wardName}")
        setupInfoItem(binding.layoutMohalla, R.drawable.gps, "MOHALLA", data.mohallaName)
        setupInfoItem(binding.layoutAddress, R.drawable.gps, "ADDRESS", "${data.address1}, ${data.address2}")

        // Complainant Info Section
        setupInfoItem(binding.layoutName, R.drawable.profile_icon, "NAME", data.name)
        setupInfoItem(binding.layoutFather, R.drawable.profile_icon, "FATHER/HUSBAND", data.fatherHusbandName)
        setupInfoItem(binding.layoutMobile, R.drawable.phone, "MOBILE", data.mobile)
        setupInfoItem(binding.layoutEmail, R.drawable.baseline_email_24, "EMAIL", data.email)

        // Assigned Officials Section
        binding.tvEmpName.text = data.assignedEmpName
        binding.tvEmpDetails.text = "${data.assignedEmpPost} • ${data.assignedEmpMobile}"
        binding.tvOffName.text = data.assignedOffName
        binding.tvOffDetails.text = "${data.assignedOffPost} • ${data.assignedOffMobile}"

        // Resolution Section
        setupInfoItem(binding.layoutCloseDate, R.drawable.clock, "CLOSE DATE", data.closeDate)
        setupInfoItem(binding.layoutCloseRemark, R.drawable.img_01, "CLOSE REMARK", data.closeRemark)
    }

    private fun setupInfoItem(itemBinding: ItemTrackInfoBinding, iconRes: Int, label: String, value: String?) {
        itemBinding.ivIcon.setImageResource(iconRes)
        itemBinding.tvLabel.text = label
        itemBinding.tvValue.text = value ?: "N/A"
    }

    private fun makeCall(phoneNumber: String?) {
        phoneNumber?.let {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it"))
            startActivity(intent)
        }
    }
}
