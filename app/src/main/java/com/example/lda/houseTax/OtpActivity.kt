package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lda.BaseActivity
import com.example.lda.MainMenu
import com.example.lda.R
import com.example.lda.databinding.ActivityOtpBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.data.SendOtpRequest
import com.example.lda.houseTax.data.VerifyOtpRequest
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.utils.LoderHelper


class OtpActivity : BaseActivity() {
    lateinit var binding: ActivityOtpBinding
    lateinit var viewModel: PaymentViewModel
    private lateinit var loderHelper: LoderHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= DataBindingUtil.setContentView(this, R.layout.activity_otp)
        viewModel= ViewModelProvider(this)[PaymentViewModel::class.java]
        supportActionBar?.hide()
        loderHelper=LoderHelper(this)

        binding.otp1.moveTo(binding.otp2)
        binding.otp2.moveTo(binding.otp3)
        binding.otp3.moveTo(binding.otp4)
        binding.otp4.moveTo(binding.otp5)
        binding.otp5.moveTo(binding.otp6)
        binding.otp6.moveTo(null)

        val mobileNo = intent.getStringExtra("mobileNo") ?: ""
        binding.btnVerify.setOnClickListener {
            val request = VerifyOtpRequest(
                mobileNo = mobileNo,
                otp = getEnteredOtp()
            )
//            viewModel.otpVerification(request)
        }

        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )
        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
//        observeViewModel()
    }

//    private fun observeViewModel(){
//        viewModel.otpVerification.observe(this){
//            if (it.success==true){
//            val intent= Intent(this, MainMenu::class.java)
//            startActivity(intent)
//            }
//            else{
//                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
//            }
//        }
//        viewModel.isLoading.observe(this){
//            if (it){
//                loderHelper.startLoadingDialog("Please wait.")
//            }else{
//                loderHelper.dismissDialog()
//            }
//        }
//    }

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


    private fun getEnteredOtp(): String {
        return binding.otp1.text.toString() +
                binding.otp2.text.toString() +
                binding.otp3.text.toString() +
                binding.otp4.text.toString() +
                binding.otp5.text.toString() +
                binding.otp6.text.toString()
    }


}

//        binding.tvRegisterHere.setOnClickListener {
//            val intent= Intent(this, RegisterMobileNumberActivity::class.java)
//            startActivity(intent)
//        }
