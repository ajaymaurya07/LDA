package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.lda.BaseActivity
import com.example.lda.MainMenu
import com.example.lda.R
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.loginDetails.SignInActivity
import com.example.lda.houseTax.utils.PreferenceManager

class LauncherActivity : BaseActivity() {
    private lateinit var preferenceManager: PreferenceManager
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

        val loginFlag= preferenceManager.isLogin()
//        val propertyIdFlag= preferenceManager.getPropertyId()
        val userID= preferenceManager.getUserId()

        if (!userID.isNullOrEmpty()){
            navigateToDash()
        }
        else if (loginFlag){
            navigateToPropertySearchScreen()
        }else{
            navigateToLoginScreen()
        }

    }



    private fun navigateToLoginScreen() {
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }, 3000) // seconds
    }

    private fun navigateToPropertySearchScreen() {
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, PropertySearchActivity::class.java)
            startActivity(intent)

            finish() // splash ko back stack se hata de

        }, 3000) // 3 seconds
    }


    private fun navigateToDash() {
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)

            finish() // splash ko back stack se hata de

        }, 3000) // 3 seconds
    }

}