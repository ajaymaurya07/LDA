package com.example.lda

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.lda.eCourtUi.MainActivity


class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        Handler().postDelayed({
            val intent = Intent(this@LauncherActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }

}

