package com.example.lda.houseTax.loginDetails

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lda.BaseActivity
import com.example.lda.R
import com.example.lda.databinding.ActivityForgotPasswordBinding
import com.example.lda.houseTax.data.ForgotPasswordRequest
import com.example.lda.houseTax.data.VerifyForgotPasswordOtpRequest
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.utils.HashUtils
import com.example.lda.utils.LoderHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModel: PaymentViewModel
    private lateinit var loderHelper: LoderHelper
    private lateinit var otpDialog: BottomSheetDialog

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

            val request = ForgotPasswordRequest(username = emailOrMobile)
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
            if (response != null) {
                if (response.status && response.responseCode == 1) {
                    val safeData = response.getSafeData()
                    val message = if (safeData?.email_hint != null) {
                        "${response.message}\nEmail Hint: ${safeData.email_hint}"
                    } else {
                        response.message ?: ""
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    openVerifyOtpBottomSheet(binding.etPhoneOrEmailId.text.toString().trim())
                } else {
                    AlertDialogHelper.showMessageDialog(this, response.message ?: "Failed to process request")
                }
            }
        }

        viewModel.verifyForgotPasswordOtp.observe(this) { response ->
            if (response != null) {
                if (response.status && response.responseCode == 1) {
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                    if (::otpDialog.isInitialized && otpDialog.isShowing) {
                        otpDialog.dismiss()
                    }
                    finish()
                } else {
                    AlertDialogHelper.showMessageDialog(this, response.message)
                }
            }
        }
    }

    private fun openVerifyOtpBottomSheet(username: String) {
        otpDialog = BottomSheetDialog(this, R.style.BottomSheetTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_forgot_password_otp, null)
        otpDialog.setContentView(view)

        val etOtp = view.findViewById<EditText>(R.id.etOtp)
        val etNewPassword = view.findViewById<EditText>(R.id.etNewPassword)
        val etConfirmPassword = view.findViewById<EditText>(R.id.etConfirmPassword)
        val btnVerify = view.findViewById<View>(R.id.btnVerify)

        btnVerify.setOnClickListener {
            val otp = etOtp.text.toString().trim()
            val newPass = etNewPassword.text.toString().trim()
            val confirmPass = etConfirmPassword.text.toString().trim()

            if (otp.length != 6) {
                Toast.makeText(this, "Enter 6-digit OTP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPass.isEmpty()) {
                Toast.makeText(this, "Enter new password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPass != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val hashedPass = HashUtils.sha512(newPass)
            val request = VerifyForgotPasswordOtpRequest(
                username = username,
                otp = otp,
                new_password = hashedPass,
                confirm_password = hashedPass
            )
            viewModel.verifyForgotPasswordOtp(request)
        }

        otpDialog.setOnShowListener {
            val bottomSheet = otpDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }

        otpDialog.show()
    }
}