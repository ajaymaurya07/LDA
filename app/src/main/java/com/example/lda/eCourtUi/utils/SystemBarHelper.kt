package com.example.lda.eCourtUi.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import com.example.lda.R


object SystemBarsHelper {

    fun Activity.applySafeAreaInsets(
        rootView: View,
        toolbar: View? = null,
        bottomBar: View? = null,
        lightStatusBar: Boolean = true,
        statusBarColor: Int = Color.WHITE,
    ) {

        window.statusBarColor = statusBarColor
        WindowInsetsControllerCompat(window, rootView)
            .isAppearanceLightStatusBars = lightStatusBar

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->

            // System bars (status + nav bar + cutout)
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                        WindowInsetsCompat.Type.displayCutout()
            )

            // Keyboard (IME)
            val imeInsets = insets.getInsets(
                WindowInsetsCompat.Type.ime()
            )

            // Bottom padding should be whichever is bigger
            val bottomPadding = maxOf(systemBars.bottom, imeInsets.bottom)

            view.updatePadding(
                left = systemBars.left,
                right = systemBars.right,
                top = if (toolbar == null) systemBars.top else view.paddingTop,
                bottom = if (bottomBar == null) bottomPadding else view.paddingBottom
            )

            toolbar?.updatePadding(top = systemBars.top)
            bottomBar?.updatePadding(bottom = bottomPadding)

            insets
        }

        ViewCompat.requestApplyInsets(rootView)
    }
}

