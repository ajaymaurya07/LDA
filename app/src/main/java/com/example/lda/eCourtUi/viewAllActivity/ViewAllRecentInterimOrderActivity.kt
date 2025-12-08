package com.example.lda.eCourtUi.viewAllActivity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivitySeeAllBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets

class ViewAllRecentInterimOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivitySeeAllBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=DataBindingUtil.setContentView(this,R.layout.activity_see_all)

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

        toolbar.title="Recent Interim Order"
    }
}