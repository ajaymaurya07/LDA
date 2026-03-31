package com.example.lda.houseTax.loginDetails

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.ActivitySignInBinding
import com.example.lda.houseTax.PropertySearchActivity
import com.example.lda.houseTax.data.ChallengeRequest
import com.example.lda.houseTax.data.SignInRequest
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.utils.LoderHelper

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: PaymentViewModel
    private lateinit var loderHelper: LoderHelper
    private lateinit var preferenceManager: PreferenceManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_in)
        viewModel= ViewModelProvider(this)[PaymentViewModel::class.java]
        loderHelper=LoderHelper(this)
        preferenceManager= PreferenceManager(this)



        binding.btnLogin.setOnClickListener {
            val userId = binding.etPhoneOrEmailId.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (userId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Phone Number Or Email ID and Password Required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                val challengeRequest = ChallengeRequest(
                    username = userId,
                    device_id = deviceId
                )
                viewModel.getChallenge(challengeRequest)
            }

        }

        binding.tvSignUp.setOnClickListener {
            val intent= Intent(this, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        observeViewModel()
    }


    private fun observeViewModel() {

        viewModel.isLoading.observe(this){
            if (it){
                loderHelper.startLoadingDialog("Please wait.")
            }else{
                loderHelper.dismissDialog()
            }
        }

        viewModel.challenge.observe(this) { response ->

            if (response.status==true) {

                val userId = binding.etPhoneOrEmailId.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                val request = SignInRequest(
                    username = userId,
                    password = password
                )
                viewModel.signIn(request)
            } else {
                Toast.makeText(this, response?.message ?: "Failed to get challenge", Toast.LENGTH_LONG).show()
            }
        }


        viewModel.signIn.observe(this){
            if (it.status==true){
                preferenceManager.login(true)
                preferenceManager.saveLoginMobileNumber(binding.etPhoneOrEmailId.text.toString().trim())
                preferenceManager.saveEmail(it.data?.emailId.toString())
                preferenceManager.saveUserType(it.data?.userType.toString())
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                val intent = Intent(this, PropertySearchActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            else{
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}