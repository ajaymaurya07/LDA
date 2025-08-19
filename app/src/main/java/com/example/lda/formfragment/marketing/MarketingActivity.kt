package com.example.lda.formfragment.marketing

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.lda.R
import com.example.lda.adaptor.ImageSliderAdaptor
import me.relex.circleindicator.CircleIndicator3

class MarketingActivity : AppCompatActivity() {

//    private lateinit var viewPager: ViewPager2
//    private lateinit var adapter: ImageSliderAdaptor
//    private lateinit var indicator: CircleIndicator3
//
//    private val handler = Handler(Looper.getMainLooper())
//    private val sliderRunnable = Runnable {
//        val nextItem = (viewPager.currentItem + 1) % adapter.itemCount
//        viewPager.setCurrentItem(nextItem, true)
//        startAutoSlide() // loop again
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_marketing)


        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        val images = listOf(
            R.drawable.img_02,
            R.drawable.img_01,
            R.drawable.img_03
        )

        val tvNewsBlink = findViewById<TextView>(R.id.tvNewsBlink)
        val blinkAnimation = AlphaAnimation(0.0f, 1.0f).apply {
            duration = 500
            startOffset = 20
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        tvNewsBlink.startAnimation(blinkAnimation)




//        viewPager = findViewById(R.id.imageSlider)
//        indicator = findViewById(R.id.indicator)

//        adapter = ImageSliderAdaptor(images)
//        viewPager.adapter = adapter
//        indicator.setViewPager(viewPager)

//        startAutoSlide()
    }

//    private fun startAutoSlide() {
//        handler.postDelayed(sliderRunnable, 2000) // 2 seconds
//    }

    override fun onPause() {
        super.onPause()
//        handler.removeCallbacks(sliderRunnable) // stop when not visible
    }

    override fun onResume() {
        super.onResume()
//        startAutoSlide()
    }
}