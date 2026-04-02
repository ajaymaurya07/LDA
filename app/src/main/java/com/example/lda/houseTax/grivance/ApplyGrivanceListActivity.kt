package com.example.lda.houseTax.grivance

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lda.BaseActivity
import com.example.lda.R
import com.example.lda.adaptor.GrievanceAdapter
import com.example.lda.databinding.ActivityApplyGrivanceListBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.utils.LoderHelper

class ApplyGrivanceListActivity : BaseActivity() {
    private lateinit var binding: ActivityApplyGrivanceListBinding
    private lateinit var adapter: GrievanceAdapter
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var loaderHelper: LoderHelper
    private lateinit var viewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyGrivanceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        preferenceManager = PreferenceManager(this)
        loaderHelper = LoderHelper(this)
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]

        setupToolbar()
        setupRecyclerView()
        observeViewModel()
        fetchGrievanceDetails()
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
            intent.putExtra("grievanceNo", grievance.grievanceNo)
            startActivity(intent)
        }
        binding.rvGrievances.apply {
            layoutManager = LinearLayoutManager(this@ApplyGrivanceListActivity)
            adapter = this@ApplyGrivanceListActivity.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) {
            if (it) {
                loaderHelper.startLoadingDialog("Fetching grievance list...")
            } else {
                loaderHelper.dismissDialog()
            }
        }

        viewModel.grievanceDetails.observe(this) { response ->
            if (response != null) {
                if (response.success == true) {
                    val list = response.data
                    if (!list.isNullOrEmpty()) {
                        adapter.updateData(list)
                        binding.noDataText.visibility = View.GONE
                    } else {
                        binding.noDataText.visibility = View.VISIBLE
                        binding.noDataText.text = "No grievances found."
                    }
                } else {
                    Toast.makeText(this, "Failed to fetch grievances: ${response.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchGrievanceDetails() {
        val email = preferenceManager.getEmail()
        if (email.isNullOrEmpty()) {
            Toast.makeText(this, "Email not found. Please login again.", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.fetchGrievanceDetails(email, this, preferenceManager)
    }
}
