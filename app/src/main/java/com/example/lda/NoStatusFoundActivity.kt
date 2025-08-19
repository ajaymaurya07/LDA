package com.example.lda

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.adaptor.mutation.MutationTrackStatus
import com.example.lda.utils.dataClass.FormSummary

class NoStatusFoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_no_status_found)

        val recyclerView = findViewById<RecyclerView>(R.id.recyler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        setupBackButton()

        val dummyList = listOf(

            FormSummary(
                "Details",
                listOf(
                    "Reason: Gift Deed",
                    "Property Type: Residential",
                    "DA Property Code: 123456",
                    "Allottee: Mr. Ramesh Kumar",
                    "Father/Spouse: Shyam Lal",
                    "Mobile: 9876543210",
                    "Email: ramesh@test.com",
                    "Registry Date: 12/08/2024",
                    "Total Cost: ₹50,00,000",
                    "Property No: 45/A",
                    "Sector: Gomti Nagar",
                    "Scheme: Housing Yojna",
                    "Land Use: Residential",
                    "Area: 120 sqm",
                    "Covered: 100 sqm",
                    "Floor: 2",
                    "Current Value: ₹60,00,000",
                    "Outstanding: ₹2,00,000",
                    "Intermediate Sales: 1",
                    "Image 1: AadharCard.jpg"
                ),
                "Pending"
            )
        )

        recyclerView.adapter = MutationTrackStatus(dummyList)

    }
    private fun setupBackButton() {
        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}