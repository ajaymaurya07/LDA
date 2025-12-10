package com.example.lda.houseTax

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lda.R
import com.example.lda.adaptor.FilterAdapter
import com.example.lda.databinding.ActivityPropertySearchBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets

class PropertySearchActivity : AppCompatActivity() {
    lateinit var binding: ActivityPropertySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding= DataBindingUtil.setContentView(this, R.layout.activity_property_search)

        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        val filters = listOf(
            "By Owner Details",
            "By Property ID",
            "By House No",
            "By Location Details",
            "By Payment Details",
            "By Ward & House No"
        )

        binding.filterRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.filterRecycler.adapter = FilterAdapter(filters) {
            showForm(it)
        }
    }

    private fun showForm(selected: String) {

        // hide all
        binding.formOwner.visibility = View.GONE
        binding.formPropertyId.visibility = View.GONE
        binding.formHouse.visibility = View.GONE
        binding.formLocation.visibility = View.GONE
        binding.formPayment.visibility = View.GONE
        binding.formWardHouse.visibility = View.GONE

        // show selected
        when (selected) {
            "By Owner Details" -> binding.formOwner.visibility = View.VISIBLE
            "By Property ID" -> binding.formPropertyId.visibility = View.VISIBLE
            "By House No" -> binding.formHouse.visibility = View.VISIBLE
            "By Location Details" -> binding.formLocation.visibility = View.VISIBLE
            "By Payment Details" -> binding.formPayment.visibility = View.VISIBLE
            "By Ward & House No" -> binding.formWardHouse.visibility = View.VISIBLE
        }
    }
}
