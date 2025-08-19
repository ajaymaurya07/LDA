package com.example.lda.formfragment.parking

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
import com.example.lda.adaptor.ParkingHistoryAdaptor
import com.example.lda.utils.dataClass.ParkingHistory

class ParkingHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_parking_history)

        val recyclerView = findViewById<RecyclerView>(R.id.parkingRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Dummy Data
        val parkingData = listOf(
            ParkingHistory("Gomti Nagar Parking", "12/08/2024", "2 hours", "₹50"),
            ParkingHistory("Hazratganj Parking", "14/08/2024", "3 hours", "₹80"),
            ParkingHistory("Alambagh Parking", "15/08/2024", "1.5 hours", "₹40"),
            ParkingHistory("Charbagh Parking", "16/08/2024", "4 hours", "₹100")
        )
        recyclerView.adapter = ParkingHistoryAdaptor(parkingData)
    }
}
