package com.example.lda.serviceactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivityWaterSewerageServiceBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets

class WaterSewerageServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWaterSewerageServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_water_sewerage_service)
        supportActionBar?.hide()

        applySafeAreaInsets(
            rootView = binding.root,
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        binding.navBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.applyNow.setOnClickListener {
            val intent = Intent(this, WaterSewerageFormActivity::class.java)
            startActivity(intent)
        }

        binding.trackStatus.setOnClickListener {
            // Handle track status if needed
        }
    }
}
