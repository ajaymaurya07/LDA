package com.example.lda.eCourtUi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lda.R
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.google.android.material.appbar.MaterialToolbar

class InterminOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intermin_order)
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            toolbar = findViewById(R.id.topAppBar),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = false
        )
        val toolbar = findViewById<MaterialToolbar>(R.id.toolBar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}