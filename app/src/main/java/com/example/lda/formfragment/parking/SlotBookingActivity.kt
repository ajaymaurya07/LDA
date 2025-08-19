package com.example.lda.formfragment.parking

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.adaptor.parking.ParkingAdaptor

class SlotBookingActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ParkingAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_slot_booking)

        recyclerView = findViewById(R.id.parkingRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sampleList = listOf(
            Parking("Hazratganj Parking", "Available Slots: 20"),
            Parking("Alambagh Parking", "Available Slots: 12")
        )

        adapter = ParkingAdaptor(sampleList) { parking ->
            val intent = Intent(this, BookingFormActivity::class.java)
            intent.putExtra("parkingName", parking.name)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
    }
}

data class Parking(val name: String, val slots: String)
