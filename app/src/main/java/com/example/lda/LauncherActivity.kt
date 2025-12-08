package com.example.lda

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.lda.constent.Constent
import com.example.lda.eCourtUi.LauncherViewModelFactory
import com.example.lda.eCourtUi.LoginActivity.LoginActivity
import com.example.lda.eCourtUi.db.AppDatabase
import com.example.lda.eCourtUi.utils.SharedPrefHelper
import com.example.lda.repository.CaseRepository
import com.example.lda.viewmodel.LauncherViewModel
import kotlinx.coroutines.delay


class LauncherActivity : AppCompatActivity() {

    private lateinit var viewModel: LauncherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_launcher)
        val userId = SharedPrefHelper.getUserId(this)
        val userType = SharedPrefHelper.getUserType(this)

        lifecycleScope.launchWhenStarted {
            delay(1000) // optional splash delay
            if (userId.isNullOrEmpty() || userType.isNullOrEmpty()) {
                navigateToLogin()
            }
            else{
                navigateToMainMenu()
            }
        }
    }

//    private fun startFetchSequence() {
//        val userId = SharedPrefHelper.getUserId(this)
//        val userType = SharedPrefHelper.getUserType(this)
//
//        if (userId.isNullOrEmpty() || userType.isNullOrEmpty()) {
//            navigateToLogin()
//            return
//        }
//
//        // Initialize repository and viewmodel
//        val db = AppDatabase.getInstance(applicationContext)
//        val repo = CaseRepository(db)
//        viewModel = LauncherViewModelFactory(repo).create(LauncherViewModel::class.java)
//
//        observeRepository(repo)
//
//        // Trigger API call
//        repo.refreshAllCases(userId, Constent.VERSION, "", userType,"PWD")
//    }

//    private fun observeRepository(repo: CaseRepository) {
//        // Loading state
//        repo.isCaseLoading.observe(this) { isLoading ->
//            if (isLoading) {
//                // Show loader if required
//            } else {
//                // Hide loader
//            }
//        }
//
//        // Data state
//        repo.allCases.observe(this) { response ->
//            if (response == null) {
//                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
//                navigateToMainMenu()
//                return@observe
//            }
//
//            if (response.result != null) {
//                // ✅ Success response
//                Toast.makeText(
//                    this,
//                    "Data synced: ${response.result.size} records",
//                    Toast.LENGTH_SHORT
//                ).show()
//                navigateToMainMenu()
//
//            } else {
//                // ❌ Error response
//                Toast.makeText(
//                    this,
//                    "Sync failed: [${response.statusCode}] ${response.statusMessage}",
//                    Toast.LENGTH_SHORT
//                ).show()
//                // still go to main menu for offline usage
//                navigateToMainMenu()
//            }
//        }
//    }

    private fun navigateToMainMenu() {
        val intent = Intent(this@LauncherActivity, MainMenu::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this@LauncherActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}


