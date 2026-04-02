package com.example.lda.houseTax.loginDetails

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lda.BaseActivity
import com.example.lda.R
import com.example.lda.databinding.ActivitySignInBinding
import com.example.lda.houseTax.PropertySearchActivity
import com.example.lda.houseTax.data.ChallengeRequest
import com.example.lda.houseTax.data.SignInRequest
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.utils.DeviceUtils
import com.example.lda.utils.HashUtils
import com.example.lda.utils.LoderHelper
import java.util.UUID

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: PaymentViewModel
    private lateinit var loderHelper: LoderHelper
    private lateinit var preferenceManager: PreferenceManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        loderHelper = LoderHelper(this)
        preferenceManager = PreferenceManager(this)



        binding.btnLogin.setOnClickListener {
            val userId = binding.etPhoneOrEmailId.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (userId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Phone Number Or Email ID and Password Required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val challengeRequest = ChallengeRequest(
                    username = userId,
                    device_id = DeviceUtils.getDeviceId(this)
                )
                viewModel.getChallenge(challengeRequest)
            }

        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
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

        viewModel.challenge.observe(this) { response ->

            if (response.status==true && response.responseCode==1) {

                val userId = binding.etPhoneOrEmailId.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                val challengeData = response.data!!
                val challenge = challengeData.challenge
                val challengeId = challengeData.challenge_id
                val timestamp = challengeData.timestamp
                val nonce = UUID.randomUUID().toString().replace("-", "").take(16)

                // hash = SHA-512( SHA-512(password) + challenge + timestamp + nonce )
                val hashedPassword = HashUtils.sha512(password)
                val hashInput = hashedPassword + challenge + timestamp + nonce
                val finalHash = HashUtils.sha512(hashInput)

                val request = SignInRequest(
                    username = userId,
                    device_id = DeviceUtils.getDeviceId(this),
                    challenge_id = challengeId,
                    timestamp = timestamp,
                    nonce = nonce,
                    hash = finalHash
                )
                viewModel.signIn(request)
            } else {
                AlertDialogHelper.showMessageDialog(this, response?.message ?: "Failed to get challenge")
            }
        }


        viewModel.signIn.observe(this) {
            if (it.status == true && it.responseCode == 1) {
                preferenceManager.login(true)
                preferenceManager.saveLoginMobileNumber(binding.etPhoneOrEmailId.text.toString().trim())
                preferenceManager.saveEmail(it.data?.emailId.toString())
                preferenceManager.saveUserType(it.data?.userType.toString())
                preferenceManager.saveAccessToken(it.data?.accessToken.toString())
                preferenceManager.saveRefreshToken(it.data?.refreshToken.toString())
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                val intent = Intent(this, PropertySearchActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                AlertDialogHelper.showMessageDialog(this, it?.message ?: "Some thing went wrong")
            }
        }
    }
}
