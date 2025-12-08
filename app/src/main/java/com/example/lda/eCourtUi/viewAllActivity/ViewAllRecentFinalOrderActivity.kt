package com.example.lda.eCourtUi.viewAllActivity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.lda.R
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets

class ViewAllRecentFinalOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_all_recent_final_order)
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            toolbar = findViewById(R.id.topAppBar),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = false
        )
    }
}