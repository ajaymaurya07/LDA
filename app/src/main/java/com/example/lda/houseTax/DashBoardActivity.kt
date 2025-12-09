package com.example.lda.houseTax

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.lda.R
import com.example.lda.utils.SystemBarsHelper.applySafeAreaInsets

class DashBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(R.layout.activity_dash_board)



        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            bottomBar = findViewById(R.id.bottomNavigationView),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )
    }
}