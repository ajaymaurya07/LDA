package com.example.lda

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.lda.databinding.ActivityMainMenuBinding
import com.example.lda.eCourtUi.CauseListFragment
import com.example.lda.eCourtUi.NotificationFragment
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenu : AppCompatActivity() {
    lateinit var binding: ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_main_menu)

        enableEdgeToEdge()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

//        binding.bottomNavigationView.getOrCreateBadge(R.id.nav_notifications).number=5


        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            bottomBar = findViewById(R.id.bottomNavigationView),
            toolbar = findViewById(R.id.topAppBar),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = false,
        )

        val causeListFragment = CauseListFragment()
        val notificationFragment = NotificationFragment()
        val profileFragment = ProfileFragment()
        val dashBoardFragment = DasBoardFragment()

        setCurrentFragment(dashBoardFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home ->setCurrentFragment(dashBoardFragment)
                R.id.cause_list -> setCurrentFragment(causeListFragment)
                R.id.nav_notifications->setCurrentFragment(notificationFragment)
                R.id.profile -> setCurrentFragment(profileFragment)
            }
            true
        }

    }



    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}