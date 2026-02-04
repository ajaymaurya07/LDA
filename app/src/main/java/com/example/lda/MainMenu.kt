package com.example.lda

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lda.databinding.ActivityMainMenuBinding
import com.example.lda.eCourtUi.CauseListFragment
import com.example.lda.eCourtUi.NotificationFragment
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.DashboradFragment
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PropertyDetailsViewmodel
import com.example.lda.model.OwnerDetails
import com.example.lda.model.PropertyDetails
import com.example.lda.utils.LoderHelper
import com.example.lda.utils.dataClass.PropertyDetailsRequest
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenu : AppCompatActivity() {
    lateinit var binding: ActivityMainMenuBinding
    lateinit var viewModel:PropertyDetailsViewmodel
    private lateinit var loderHelper: LoderHelper
    lateinit var preferenceManager: PreferenceManager


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
        viewModel.propertyDetailsData()

    }




    private fun observerLoader(){
        viewModel.isLoading.observe(this){
            if (it){
                loderHelper.startLoadingDialog("Data is loading...")
            }else{
                loderHelper.dismissDialog()
            }
        }
    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}
