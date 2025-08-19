package com.example.lda

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()

        // Optional: Android 12+ native splash support
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val logo = findViewById<ImageView>(R.id.logo)
        val appName = findViewById<TextView>(R.id.appName)

        // Initial state
        logo.scaleX = 0.85f
        logo.scaleY = 0.85f
        logo.alpha = 0f
        appName.alpha = 0f

        // Fade + Zoom In
        val zoomInX = ObjectAnimator.ofFloat(logo, View.SCALE_X, 0.85f, 1.1f)
        val zoomInY = ObjectAnimator.ofFloat(logo, View.SCALE_Y, 0.85f, 1.1f)
        val fadeInLogo = ObjectAnimator.ofFloat(logo, View.ALPHA, 0f, 1f)

        // Zoom Out
        val zoomOutX = ObjectAnimator.ofFloat(logo, View.SCALE_X, 1.1f, 1f)
        val zoomOutY = ObjectAnimator.ofFloat(logo, View.SCALE_Y, 1.1f, 1f)

        // App Name Fade-in
        val fadeInAppName = ObjectAnimator.ofFloat(appName, View.ALPHA, 0f, 1f)

        // Durations
        zoomInX.duration = 800
        zoomInY.duration = 800
        fadeInLogo.duration = 800
        zoomOutX.duration = 500
        zoomOutY.duration = 500
        fadeInAppName.duration = 700

        // Interpolators
        zoomInX.interpolator = OvershootInterpolator(2.5f)
        zoomInY.interpolator = OvershootInterpolator(2.5f)
        zoomOutX.interpolator = DecelerateInterpolator()
        zoomOutY.interpolator = DecelerateInterpolator()

        // Animation flow
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(zoomInX, zoomInY, fadeInLogo)
        animatorSet.play(zoomOutX).after(zoomInX)
        animatorSet.play(zoomOutY).after(zoomInY)
        animatorSet.play(fadeInAppName).after(500) // App name fades in midway

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@LauncherActivity, MainMenu::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }, 300) // small delay for elegance
            }
        })

        animatorSet.start()
    }
}

