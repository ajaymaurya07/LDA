package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()

        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.btnSendOtp.setOnClickListener {
            val intent= Intent(this, OtpActivity::class.java)
            startActivity(intent)
        }
    }
}