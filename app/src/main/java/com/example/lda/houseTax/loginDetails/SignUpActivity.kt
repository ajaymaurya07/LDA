package com.example.lda.houseTax.loginDetails

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.ActivitySignUpBinding
import com.example.lda.houseTax.data.SignUpRequest
import com.example.lda.houseTax.data.VerifyOtpMailRequest
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.utils.LoderHelper
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.AccountPicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var viewModel: PaymentViewModel
    private lateinit var loderHelper: LoderHelper
    private lateinit var phoneNumberHintLauncher: ActivityResultLauncher<IntentSenderRequest>
    private lateinit var emailPickerLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog= BottomSheetDialog(this)
        viewModel= ViewModelProvider(this)[PaymentViewModel::class.java]
        loderHelper=LoderHelper(this)



        binding.btnSignUp.setOnClickListener {
            Log.d("TAG", "onCreate: ${binding.etMobileNumber.text}")
            if (validateInput()) {
                val name = binding.etUserName.text.toString().trim()
                val mobile = binding.etMobileNumber.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                val request = SignUpRequest(
                    name = name,
                    mobile_no = mobile,
                    email = email,
                    password = password
                )
                viewModel.signUp(request)

            }
        }



        binding.etMobileNumber.setOnClickListener {
            fetchPhoneNumberFromDevice()
        }

        binding.etEmail.setOnClickListener {
            fetchEmailFromDevice()
        }



        phoneNumberHintLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {

                    val phoneNumber = Identity.getSignInClient(this)
                            .getPhoneNumberFromIntent(result.data)

                    phoneNumber.let {
                        val cleanNumber = it
                            .replace(Regex("[^0-9]"), "")
                            .takeLast(10)

                        binding.etMobileNumber.setText(cleanNumber)
                    }
                }
            }



        emailPickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val email =
                        result.data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)

                    if (!email.isNullOrEmpty()) {
                        binding.etEmail.setText(email)
                    }
                }
            }


        observeViewModel()
    }


    private fun fetchEmailFromDevice() {

        val intent = AccountPicker.newChooseAccountIntent(
            null,
            null,
            arrayOf("com.google"), // ONLY Gmail
            false,
            null,
            null,
            null,
            null
        )

        emailPickerLauncher.launch(intent)
    }




    private fun fetchPhoneNumberFromDevice() {
        val request = GetPhoneNumberHintIntentRequest.builder().build()
        Identity.getSignInClient(this)
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener { intentSender ->
                phoneNumberHintLauncher.launch(
                    IntentSenderRequest.Builder(intentSender).build()
                )
            }
            .addOnFailureListener {
            }
    }





    private fun maskMobileNumber(mobile: String): String {
        return if (mobile.length >= 4) {
            "******" + mobile.takeLast(4)
        } else {
            mobile
        }
    }

    private fun observeViewModel() {
        viewModel.signUpData.observe(this){
            if (it.status==true){
                it.data?.email?.let {
                    it1 -> openOtpBottomSheet(it1)
                }
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(this){
            if (it){
                loderHelper.startLoadingDialog("Please wait.")
            }else{
                loderHelper.dismissDialog()
            }
        }

        viewModel.otpVerificationMail.observe(this){
            if (it.status==true){
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                dialog.dismiss()

                val intent = Intent(this, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            else{
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }




    private fun validateInput(): Boolean {

        val name = binding.etUserName.text.toString().trim()
        val mobile = binding.etMobileNumber.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Name required", Toast.LENGTH_LONG).show()
            return false
        }

        if (mobile.isEmpty()) {
            Toast.makeText(this, "Mobile number required", Toast.LENGTH_LONG).show()
            return false
        }

        if (!isValidMobile(mobile)) {
            Toast.makeText(this, "Enter valid 10 digit mobile number", Toast.LENGTH_LONG).show()
            return false
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Email required", Toast.LENGTH_LONG).show()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter valid email address", Toast.LENGTH_LONG).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password required", Toast.LENGTH_LONG).show()
            return false
        }


        if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Confirm password required", Toast.LENGTH_LONG).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Password & Confirm Password does not match", Toast.LENGTH_LONG).show()
            return false
        }


        return true
    }

    private fun isValidMobile(mobile: String): Boolean {
        return mobile.length == 10 && mobile.matches(Regex("^[6-9][0-9]{9}$"))
    }


    private fun openOtpBottomSheet(email: String) {

        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_email_otp, null)
        dialog.setContentView(view)


        val verifyOtpButton = view.findViewById<View>(R.id.btnVerifyOtp)
        val etOtp = view.findViewById<EditText>(R.id.etOtp)

        verifyOtpButton.setOnClickListener {
            val otp = etOtp.text.toString().trim()
            if (otp.isEmpty()) { Toast.makeText(this, "Enter OTP first", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val request = VerifyOtpMailRequest(
                email = email,
                otp = otp
            )
            viewModel.otpVerificationMail(request)
        }

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val layoutParams = it.layoutParams
                layoutParams.height = (resources.displayMetrics.heightPixels * 0.65).toInt()
                it.layoutParams = layoutParams

                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }

        dialog.show()
    }
}
