package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.lda.MainMenu
import com.example.lda.R
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.utils.PreferenceManager

class LauncherActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_launcher2)


        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        preferenceManager= PreferenceManager(this)

        val pid= preferenceManager.getPropertyId()
        if (pid!=null){
            navigateToDashBoardScreen()
        }else{
            navigateToMainScreen()
        }

    }


    private fun navigateToDashBoardScreen() {
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)

            finish() // splash ko back stack se hata de

        }, 3000) // ⏱ 3 seconds
    }

    private fun navigateToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, PropertySearchActivity::class.java)
            startActivity(intent)

            finish() // splash ko back stack se hata de

        }, 3000) // ⏱ 3 seconds
    }

}