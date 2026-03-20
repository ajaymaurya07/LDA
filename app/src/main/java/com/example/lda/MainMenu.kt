package com.example.lda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.lda.databinding.ActivityMainMenuBinding
import com.example.lda.eCourtUi.CauseListFragment
import com.example.lda.eCourtUi.NotificationFragment
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.DashboradFragment
import com.example.lda.houseTax.LoginActivity
import com.example.lda.houseTax.data.database.AppDatabase
import com.example.lda.houseTax.data.database.dao.PropertyDao
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PropertyDetailsViewmodel
import com.example.lda.houseTax.viewmodel.PropertyIdFromDb
import com.example.lda.model.OwnerDetails
import com.example.lda.model.PropertyDetails
import com.example.lda.utils.LoderHelper
import com.example.lda.utils.dataClass.PropertyDetailsRequest
import com.example.lda.utils.permission.NotificationPermissionHandler
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class MainMenu : AppCompatActivity() {
    lateinit var binding: ActivityMainMenuBinding
    lateinit var viewModel:PropertyDetailsViewmodel
    private lateinit var loderHelper: LoderHelper
    lateinit var preferenceManager: PreferenceManager
    private lateinit var notificationPermissionHandler: NotificationPermissionHandler



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_main_menu)
        viewModel= ViewModelProvider(this)[PropertyDetailsViewmodel::class.java]


        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            bottomBar = findViewById(R.id.bottomNavigationView),
            toolbar = findViewById(R.id.topAppBar),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        loderHelper= LoderHelper(this)
        preferenceManager = PreferenceManager(this)

        observerLoader()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        val notificationFragment = NotificationFragment()
        val profileFragment = ProfileFragment()
        val dashBoardFragment = DashboradFragment()

        setCurrentFragment(dashBoardFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home ->setCurrentFragment(dashBoardFragment)
                R.id.nav_notifications->setCurrentFragment(notificationFragment)
                R.id.profile -> setCurrentFragment(profileFragment)
            }
            true
        }


        val pid=preferenceManager.getPropertyId()
        viewModel.pid.value=pid
        viewModel.propertyDetailsRequest= PropertyDetailsRequest(propertyId = pid!!)
        viewModel.propertyDetailsData(preferenceManager.getLoginMobileNumber().toString())

//        FirebaseMessaging.getInstance().token  // get token for more reliable update fcm token but jajuri nhi h
//            .addOnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.d("TAG", "Fetching token failed")
//                    return@addOnCompleteListener
//                }
//                val token = task.result
//                Log.d("TAG", "Token: $token")
//            }

    }


    override fun onStart() {
        super.onStart()
        notificationPermissionHandler = NotificationPermissionHandler(this)
        notificationPermissionHandler.checkPermissions()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        notificationPermissionHandler.handlePermissionResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    private fun observerLoader(){
        viewModel.isLoading.observe(this){
            if (it){
                loderHelper.startLoadingDialog("Data is loading...")
            }else{
                loderHelper.dismissDialog()
            }
        }

//        viewModel.dataList.observe(this){
//
//            if (it.success == true){
//                Log.d("TAG", "mainmenu: $it")
//            }
//
//        }
    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}
