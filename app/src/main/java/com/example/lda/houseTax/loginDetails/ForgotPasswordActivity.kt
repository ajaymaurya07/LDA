package com.example.lda.houseTax.loginDetails

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.ActivityForgotPasswordBinding
import com.example.lda.houseTax.data.ForgotPasswordRequest
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.utils.LoderHelper

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModel: PaymentViewModel
    private lateinit var loderHelper: LoderHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        loderHelper = LoderHelper(this)

        binding.btnSubmit.setOnClickListener {
            val emailOrMobile = binding.etPhoneOrEmailId.text.toString().trim()

            if (emailOrMobile.isEmpty()) {
                Toast.makeText(this, "Please enter registered Email or Mobile Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = ForgotPasswordRequest(email_or_mobile = emailOrMobile)
            viewModel.forgotPassword(request)
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) {
            if (it) {
                loderHelper.startLoadingDialog("Please wait.")
            } else {
                loderHelper.dismissDialog()
            }
        }

        viewModel.forgotPassword.observe(this) { response ->
                if (response.status && response.responseCode == 1) {
                    val message = if (response.data?.email_hint != null) {
                        "${response.message}\nEmail Hint: ${response.data.email_hint}"
                    } else {
                        response.message
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                } else {
                    AlertDialogHelper.showMessageDialog(this, response.message ?: "Failed to process request")
                }
        }
    }
}