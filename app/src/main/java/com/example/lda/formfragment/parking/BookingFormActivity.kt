package com.example.lda.formfragment.parking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lda.R

class BookingFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_form)

        val btnProceed: Button = findViewById(R.id.btnProceedPayment)
        val parkingName = intent.getStringExtra("parkingName") ?: "Parking"

        btnProceed.setOnClickListener {
            val intent = Intent(this, BookingSuccessActivity::class.java)
            intent.putExtra("parkingName", parkingName)
            startActivity(intent)
        }
    }
}
