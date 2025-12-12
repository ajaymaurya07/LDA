package com.example.lda.houseTax

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lda.R
import com.example.lda.databinding.ActivityGravianceBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets

class GravianceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGravianceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGravianceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = binding.root,
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupDropdowns()
    }

    private fun setupDropdowns() {

        val zones = listOf("Zone 1", "Zone 2", "Zone 3")
        val wards = listOf("Ward 1", "Ward 2", "Ward 3")
        val categories = listOf("Property Tax", "Payment Not Updated", "Other")

        binding.etZone.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, zones))
        binding.etWard.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, wards))
        binding.etCategory.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, categories))

        // Auto-select default values
        binding.etZone.setText(zones[0], false)
        binding.etWard.setText(wards[0], false)

        // Disable dropdown completely
        disableDropdown(binding.etZone)
        disableDropdown(binding.etWard)
    }

    private fun disableDropdown(view: AutoCompleteTextView) {
        view.keyListener = null              // typing disable
        view.isCursorVisible = false
        view.isFocusable = false             // no cursor focus
        view.isFocusableInTouchMode = false
        view.isClickable = false             // no click
        view.isLongClickable = false         // no long click

        // Fully disable touch so dropdown won't open
        view.setOnTouchListener { _, _ -> true }

        // Grey color (readonly look)
        view.setTextColor(Color.GRAY)
    }




}
