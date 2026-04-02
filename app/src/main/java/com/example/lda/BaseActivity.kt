package com.example.lda


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.lda.houseTax.LoginActivity
import com.example.lda.houseTax.data.database.AppDatabase
import com.example.lda.houseTax.loginDetails.SignInActivity
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeLogout()
    }

    private fun observeLogout() {
        SessionManager.logoutLiveData.observe(this) { isLogout ->
            if (isLogout == true) {
                performLogout()
            }
        }
    }

    private fun performLogout() {
        val preferenceManager = PreferenceManager(this)

        Log.d("TAG", "performLogout: done")

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                preferenceManager.clearAll()
                AppDatabase.getDatabase(this@BaseActivity).clearAllTables()

            } catch (e: Exception) {
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        finish()
    }
}