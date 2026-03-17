package com.example.lda.houseTax.grivance

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lda.R
import com.example.lda.adaptor.GrievanceAdapter
import com.example.lda.databinding.ActivityApplyGrivanceListBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.model.GrievanceStatusData

class ApplyGrivanceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyGrivanceListBinding
    private lateinit var adapter: GrievanceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyGrivanceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        setupToolbar()
        setupRecyclerView()
        loadMockData()
    }

    private fun setupToolbar() {
        applySafeAreaInsets(
            rootView = binding.root,
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )
        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        adapter = GrievanceAdapter(emptyList()) { grievance ->
            val intent = Intent(this, TrackGrivanceActivity::class.java)
            // In a real app, you would pass the ID or the whole object
            // intent.putExtra("complaintId", grievance.complaintId)
            startActivity(intent)
        }
        binding.rvGrievances.apply {
            layoutManager = LinearLayoutManager(this@ApplyGrivanceListActivity)
            adapter = this@ApplyGrivanceListActivity.adapter
        }
    }

    private fun loadMockData() {
        val mockList = listOf(
            GrievanceStatusData(
                complaintId = "PGF43925260008472",
                categoryName = "Garbage and Cleanliness",
                subCategoryName = "Lifting of Malwa",
                ulbName = "Nagar Nigam Lucknow",
                mohallaName = "KALYANPUR WEST",
                zoneName = "Zone-3",
                wardName = "SHANKAR PURWA-2",
                complaintDate = "20-02-2026",
                landmark = "Near Post Office",
                complaintDesc = "Water supply issue",
                name = "Prasoon Mishra",
                fatherHusbandName = "p K Mishra",
                mobile = "9999933210",
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
            ),
            GrievanceStatusData(
                complaintId = "PGF43925260008473",
                categoryName = "Street Light",
                subCategoryName = "New Light Installation",
                status = "In Progress",
                complaintDate = "21-02-2026"
                // ... other fields can be null or empty for mock
            )
        )
        adapter.updateData(mockList)
    }
}