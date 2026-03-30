package com.example.lda.houseTax.loginDetails

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SubscriptionManager
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.ActivitySignUpBinding
import com.example.lda.houseTax.data.SignUpRequest
import com.example.lda.houseTax.data.VerifyOtpMailRequest
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.utils.LoderHelper
import com.google.android.gms.common.AccountPicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.InputType
import com.example.lda.utils.AlertDialogHelper

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var viewModel: PaymentViewModel
    private lateinit var loderHelper: LoderHelper
    private lateinit var emailPickerLauncher: ActivityResultLauncher<Intent>
    private val PERMISSION_REQUEST_CODE = 100



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog= BottomSheetDialog(this)
        viewModel= ViewModelProvider(this)[PaymentViewModel::class.java]
        loderHelper=LoderHelper(this)

        enableEdgeToEdge()


        binding.btnSignUp.setOnClickListener {
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
            getNumber()
        }

        binding.etEmail.setOnClickListener {
            fetchEmailFromDevice()
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




    private fun getNumber() {
        if (hasPermissions()) {
            showNumberList()
        } else {
            requestPermission()
        }
    }





    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_PHONE_STATE
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE &&
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        ) {
            showNumberList()
        } else {
            val deniedPermissions = permissions.filterIndexed { index, _ ->
                grantResults[index] != PackageManager.PERMISSION_GRANTED
            }

            handlePermissionDenial(deniedPermissions)
        }
    }


    private fun handlePermissionDenial(deniedPermissions: List<String>) {

        val permanentlyDenied = deniedPermissions.filter {
            !ActivityCompat.shouldShowRequestPermissionRationale(this, it)
        }

        if (permanentlyDenied.isNotEmpty()) {
            showGoToSettingsDialog()
        } else {
            showRationaleDialog()
        }
    }


    private fun showRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("Phone permission is required to detect your number.")
            .setPositiveButton("Allow") { _, _ ->
                requestPermission()
            }
            .setNegativeButton("Cancel", null)
            .setCancelable(false)
            .show()
    }
    private fun showGoToSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("Please enable permission from App Settings.")
            .setPositiveButton("Open Settings") { _, _ ->

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", packageName, null)
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .setCancelable(false)
            .show()
    }


    private fun showNumberList() {

        val subscriptionManager = getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

        val subscriptionList = subscriptionManager.activeSubscriptionInfoList

        if (subscriptionList.isNullOrEmpty()) {
            Toast.makeText(this, "No SIM found", Toast.LENGTH_SHORT).show()
            return
        }

        val numberList = mutableListOf<String>()

        for (info in subscriptionList) {
            val number = info.number
            if (!number.isNullOrEmpty()) {
                numberList.add("${info.carrierName} - $number")
            }
        }

        if (numberList.isEmpty()) {
            binding.showWorking.visibility=View.VISIBLE
            binding.etMobileNumber.apply {
                isFocusable = true
                isFocusableInTouchMode = true
                isClickable = true
                isCursorVisible = true
                inputType = InputType.TYPE_CLASS_PHONE
                requestFocus()
            }
            return
        }

        // Show dialog with numbers
        AlertDialog.Builder(this)
            .setTitle("Select Phone Number")
            .setItems(numberList.toTypedArray()) { _, which ->
                val selected = numberList[which]
                val cleanNumber = selected.substringAfter("-").trim().takeLast(10)
                binding.etMobileNumber.setText(cleanNumber)
            }
            .show()
    }



    private fun hasPermissions(): Boolean {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_NUMBERS
            ) == PackageManager.PERMISSION_GRANTED &&

                    ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_PHONE_STATE
                    ) == PackageManager.PERMISSION_GRANTED

        } else {

            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }




    private fun observeViewModel() {
        viewModel.signUpData.observe(this){
            if (it.status==true){
                openOtpBottomSheet(binding.etEmail.text.toString())

                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
            else{
                AlertDialogHelper.showMessageDialog(this, it.message?:"")
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

        dialog = BottomSheetDialog(this,R.style.BottomSheetTheme)
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
