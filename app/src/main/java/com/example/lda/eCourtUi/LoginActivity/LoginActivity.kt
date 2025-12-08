package com.example.lda.eCourtUi.LoginActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lda.MainMenu
import com.example.lda.R
import com.example.lda.constent.Constent
import com.example.lda.databinding.ActivityLoginBinding
import com.example.lda.eCourtUi.utils.ProgressBar
import com.example.lda.eCourtUi.utils.SharedPrefHelper
import com.example.lda.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    lateinit var binding: ActivityLoginBinding
    private lateinit var progressDialog: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=DataBindingUtil.setContentView(this,R.layout.activity_login)

        viewModel=ViewModelProvider(this)[LoginViewModel::class.java]

        progressDialog = ProgressBar(this)

        binding.loginButton.setOnClickListener {
            val userId = binding.etUserid.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (userId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter User Id and Password!", Toast.LENGTH_SHORT).show()
            } else {
                performLogin(userId, password)
            }
        }

        viewModel.auth.observe(this) { response ->

            if (response?.statusCode == "200") {
//                "user_type": "admin",
//                "user_id": "admin",
//                "user_name": "Admin",
//                "mobile": "45667899",
//                "department": "PWD",
                val userId = binding.etUserid.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                val userType = response.result?.firstOrNull()?.userType ?: ""
                val department = response.result?.firstOrNull()?.department ?: ""
                val userName = response.result?.firstOrNull()?.userName ?: ""
                val mobileNumber = response.result?.firstOrNull()?.mobile ?: ""

                SharedPrefHelper.saveUserCredentials(this, userId, password, userType,department,userName,mobileNumber)
                startActivity(Intent(this, MainMenu::class.java))
                finish()
            }
        }


        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                progressDialog.startLoadingDialog("Loading Data......")
            } else {
                progressDialog.dismissDialog()
            }
        }


    }

    private fun performLogin(userId:String,password:String){
        viewModel.getAuthenticate(userId,password,Constent.VERSION)
    }
}