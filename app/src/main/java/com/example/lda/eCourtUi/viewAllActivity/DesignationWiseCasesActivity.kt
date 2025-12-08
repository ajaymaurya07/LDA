package com.example.lda.eCourtUi.viewAllActivity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivityDesignationWiseCasesBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets

class DesignationWiseCasesActivity : AppCompatActivity() {
    lateinit var binding: ActivityDesignationWiseCasesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_designation_wise_cases)

        applySafeAreaInsets(
            rootView = binding.root,
            toolbar = binding.seeAllInterimOrderNavbar.topAppBar,
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = false
        )

        val toolbar = binding.seeAllInterimOrderNavbar.toolBar
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        toolbar.title="Designation Wise Cases"

    }
}