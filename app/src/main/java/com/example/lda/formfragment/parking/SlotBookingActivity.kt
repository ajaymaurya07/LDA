package com.example.lda.formfragment.parking

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lda.R

class SlotBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_slot_booking)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setIconifiedByDefault(false)
        searchView.onActionViewExpanded()
        searchView.queryHint = "Search Parking Slots"

        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.typeface = ResourcesCompat.getFont(this, R.font.poppins_regular)
        searchEditText.textSize = 16f

    }
}