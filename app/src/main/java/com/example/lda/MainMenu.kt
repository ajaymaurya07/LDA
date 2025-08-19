package com.example.lda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lda.utils.permission.PermissionHandler
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenu : AppCompatActivity() {
    private lateinit var permissionHandler: PermissionHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        supportActionBar?.hide()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        permissionHandler= PermissionHandler(this)

        val homeFragment = HomeFragment()
        val servicesFragment = ServicesFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home ->setCurrentFragment(homeFragment)
                R.id.service -> setCurrentFragment(servicesFragment)
                R.id.profile -> setCurrentFragment(profileFragment)

            }
            true
        }

    }

    override fun onStart() {
        super.onStart()
        permissionHandler.checkPermissions()
    }

    // Override onRequestPermissionsResult to handle permission results
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHandler.handlePermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}