package com.example.lda.formfragment.parking

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lda.R

class ParkingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_parking)

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        val divSlotBooking = findViewById<LinearLayout>(R.id.div_slot_booking)
        divSlotBooking.setOnClickListener {
            val intent = Intent(this, SlotBookingActivity::class.java)
            startActivity(intent)
        }

        val divParkingHistory = findViewById<LinearLayout>(R.id.div_parking_history)
        divParkingHistory.setOnClickListener {
            val intent = Intent(this, ParkingHistoryActivity::class.java)
            startActivity(intent)
        }



    }
}