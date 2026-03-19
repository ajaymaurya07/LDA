package com.example.lda.houseTax.grivance

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lda.R
import com.example.lda.adaptor.GrievanceAdapter
import com.example.lda.databinding.ActivityApplyGrivanceListBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.model.GrievanceDetailsResponse
import com.example.lda.network.RetrofitClient
import com.example.lda.utils.LoderHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyGrivanceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyGrivanceListBinding
    private lateinit var adapter: GrievanceAdapter
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var loaderHelper: LoderHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyGrivanceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        preferenceManager = PreferenceManager(this)
        loaderHelper = LoderHelper(this)

        setupToolbar()
        setupRecyclerView()
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

    private fun fetchGrievanceDetails() {
        val email = preferenceManager.getEmail()
        if (email.isNullOrEmpty()) {
            Toast.makeText(this, "Email not found. Please login again.", Toast.LENGTH_SHORT).show()
            return
        }

        loaderHelper.startLoadingDialog("Fetching grievance list...")
        
        val requestBody = mapOf("email_id" to email)
        
        RetrofitClient.apiCall.getGrievanceDetails(1, requestBody).enqueue(object : Callback<GrievanceDetailsResponse> {
            override fun onResponse(
                call: Call<GrievanceDetailsResponse>,
                response: Response<GrievanceDetailsResponse>
            ) {
                loaderHelper.dismissDialog()
                if (response.isSuccessful && response.body()?.success == true) {
                    val list = response.body()?.data
                    if (!list.isNullOrEmpty()) {
                        adapter.updateData(list)
                        binding.noDataText.visibility = View.GONE
                    } else {
                        binding.noDataText.visibility = View.VISIBLE
                        binding.noDataText.text = "No grievances found."
                    }
                } else {
                    Toast.makeText(this@ApplyGrivanceListActivity, "Failed to fetch grievances: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GrievanceDetailsResponse>, t: Throwable) {
                loaderHelper.dismissDialog()
                Toast.makeText(this@ApplyGrivanceListActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
