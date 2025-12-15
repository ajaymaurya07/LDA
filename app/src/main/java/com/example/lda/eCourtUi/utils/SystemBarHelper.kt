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
import com.example.lda.R

//object SystemBarsHelper {
//    fun Activity.applySafeAreaInsets(
//        rootView: View,
//        toolbar: View? = null,
//        bottomBar: View? = null,
//        lightStatusBar: Boolean = true,
//        statusBarColor: Int = Color.TRANSPARENT,
//    ) {
//        window.statusBarColor = statusBarColor
//        WindowInsetsControllerCompat(window, rootView).isAppearanceLightStatusBars = lightStatusBar
//
//        // Insets listener
//        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
//            val systemBars = insets.getInsets(
//                WindowInsetsCompat.Type.systemBars() or
//                        WindowInsetsCompat.Type.displayCutout()
//            )
//            view.updatePadding(
//                left = systemBars.left,
//                right = systemBars.right,
//            )
//            if (bottomBar!=null){
//                view.updatePadding(top = systemBars.top)
//            }
//            if (toolbar!=null){
//                view.updatePadding(bottom = systemBars.bottom)
//            }
//            toolbar?.updatePadding(top = systemBars.top)
//            bottomBar?.updatePadding(bottom = systemBars.bottom)
//
//            insets
//        }
//    }
//}


object SystemBarsHelper {

    fun Activity.applySafeAreaInsets(
        rootView: View,
        toolbar: View? = null,
        bottomBar: View? = null,
        lightStatusBar: Boolean = true,
        statusBarColor: Int = Color.WHITE,
    ) {
        // If both toolbar and bottomBar are present, use primary color for status bar
        val finalStatusBarColor = if (toolbar != null && bottomBar != null) {
            getColor(com.example.lda.R.color.primary) // Replace with your primary color
        } else {
            statusBarColor
        }

        window.statusBarColor = finalStatusBarColor
        WindowInsetsControllerCompat(window, rootView).isAppearanceLightStatusBars = lightStatusBar

        // Insets listener
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                        WindowInsetsCompat.Type.displayCutout()
            )

            // Root padding
            view.updatePadding(
                left = systemBars.left,
                right = systemBars.right,
                top = if (toolbar == null) systemBars.top else view.paddingTop,
                bottom = if (bottomBar == null) systemBars.bottom else view.paddingBottom
            )

            // Toolbar and BottomBar padding
            toolbar?.updatePadding(top = systemBars.top)
            bottomBar?.updatePadding(bottom = systemBars.bottom)

            insets
        }
    }
}
