package com.example.lda.eCourtUi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivityMainBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applyEdgeToEdgeWithStatusBar
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        applyEdgeToEdgeWithStatusBar(
            rootView = binding.root,
            toolbar = binding.topAppBar,
            statusBarColor = getColor(R.color.primary)
        )

        val toolbar = findViewById<MaterialToolbar>(R.id.toolBar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}