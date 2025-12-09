package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivityOtpBinding

class OtpActivity : AppCompatActivity() {
    lateinit var binding: ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= DataBindingUtil.setContentView(this, R.layout.activity_otp)
        supportActionBar?.hide()

        binding.otp1.moveTo(binding.otp2)
        binding.otp2.moveTo(binding.otp3)
        binding.otp3.moveTo(binding.otp4)
        binding.otp4.moveTo(null)


        binding.btnVerify.setOnClickListener {
            val intent= Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
        }
    }


    private fun EditText.moveTo(next: EditText?) {
        this.addTextChangedListener {
            if (this.text.length == 1) next?.requestFocus()
        }

        this.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                if (this.text.isEmpty()) this.focusSearch(View.FOCUS_LEFT)?.requestFocus()
            }
            false
        }
    }





}