package com.example.lda.formfragment.parking

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lda.R

class BookingSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_success)

        val details: TextView = findViewById(R.id.tvBookingDetails)
        val name = intent.getStringExtra("parkingName") ?: "Parking"

        details.text = "$name\n19 Aug | 10:00 - 12:00"
    }
}
