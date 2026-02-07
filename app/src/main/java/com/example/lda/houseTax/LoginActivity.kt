package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lda.MainMenu
import com.example.lda.R
import com.example.lda.databinding.ActivityLogin2Binding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.data.SendOtpRequest
import com.example.lda.houseTax.data.VerifyOtpRequest
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.utils.LoderHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogin2Binding
    lateinit var viewmodel :PaymentViewModel
    private lateinit var loderHelper: LoderHelper
    private lateinit var phoneNumber:String
    private lateinit var dialog: BottomSheetDialog
    lateinit var sharedPreferences: PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding= DataBindingUtil.setContentView(this, R.layout.activity_login2)
        viewmodel= ViewModelProvider(this)[PaymentViewModel::class.java]
        loderHelper=LoderHelper(this)
        dialog= BottomSheetDialog(this)
        sharedPreferences= PreferenceManager(this)

        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        phoneNumber = intent.getStringExtra("phone_no").toString()
        binding.etMobile.setText(maskMobileNumber(phoneNumber))

        binding.btnSendOtp.setOnClickListener {
            if (phoneNumber.isBlank()) {
                Toast.makeText(this, "Phone number not available.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val propertyId = sharedPreferences.getPropertyId()
            if (propertyId.isNullOrEmpty()) {
                Toast.makeText(this, "Property ID missing", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val request = SendOtpRequest(
                mobileNo = phoneNumber,
                propertyId = propertyId
            )
            viewmodel.sendOtp(request)
        }
        observeViewModel()
    }



    private fun maskMobileNumber(mobile: String): String {
        return if (mobile.length >= 4) {
            "******" + mobile.takeLast(4)
        } else {
            mobile
        }
    }

    private fun observeViewModel(){
        viewmodel.sendOtp.observe(this){
            if (it.success==true){
                openOtpBottomSheet()
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }

        viewmodel.isLoading.observe(this){
            if (it){
                loderHelper.startLoadingDialog("Please wait.")
            }else{
                loderHelper.dismissDialog()
            }
        }
        viewmodel.otpVerification.observe(this){
            if (it.success==true){
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                sharedPreferences.saveUserId(it.userId.toString())
                val intent=Intent(this,MainMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            else{
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun openOtpBottomSheet() {

        dialog = BottomSheetDialog(this, R.style.BottomSheetTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_otp, null)
        dialog.setContentView(view)


        val verifyOtpButton = view.findViewById<View>(R.id.btnVerifyOtp)
        val etOtp = view.findViewById<EditText>(R.id.etOtp)

        verifyOtpButton.setOnClickListener {
            val otp = etOtp.text.toString().trim()
            if (otp.isEmpty()) { Toast.makeText(this, "Enter OTP first", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val request = VerifyOtpRequest(
                mobileNo = phoneNumber,
                otp = otp
            )
            viewmodel.otpVerification(request)
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