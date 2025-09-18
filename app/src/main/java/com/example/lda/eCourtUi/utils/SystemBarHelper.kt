package com.example.lda.eCourtUi.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding

object SystemBarsHelper {

    /**
     * Enable edge-to-edge layout and adjust status bar color.
     * Handles safe-area insets (status bar + cutout).
     */
    fun Activity.applyEdgeToEdgeWithStatusBar(
        rootView: View,
        toolbar: View,
        statusBarColor: Int
    ) {
        // ✅ Step 1: Allow edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // ✅ Step 2: Status bar color
        window.statusBarColor = statusBarColor

        // ✅ Step 3: Safe-area insets listener
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                        WindowInsetsCompat.Type.displayCutout()
            )

            // Root layout safe padding
            view.updatePadding(
                left = systemBars.left,
                right = systemBars.right,
                bottom = systemBars.bottom
            )

            // Toolbar padding only for status bar height
            toolbar.updatePadding(top = systemBars.top)

            insets
        }
    }

    /**
     * Transparent navigation bar handling across Android versions.
     */
    fun Activity.makeNavigationBarTransparent(lightIcons: Boolean = true) {
        // ✅ Let app draw behind system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        when {
            // Android 11+ (API 30+)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                window.navigationBarColor = Color.TRANSPARENT

                val controller = WindowInsetsControllerCompat(window, window.decorView)
                controller.isAppearanceLightNavigationBars = lightIcons
            }

            // Android 8.0 (API 26) to Android 10 (API 29)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                window.navigationBarColor = Color.TRANSPARENT
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            (if (lightIcons) View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR else 0)
            }

            // Android 5.0 (API 21) to Android 7.1 (API 25)
            true -> {
                window.navigationBarColor = Color.TRANSPARENT
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
    }

}