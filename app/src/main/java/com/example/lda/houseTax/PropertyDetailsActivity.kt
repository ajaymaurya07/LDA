package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.data.PropertyAdaptor
import com.example.lda.houseTax.data.PropertyModel
import com.google.android.material.appbar.MaterialToolbar

class PropertyDetailsActivity : AppCompatActivity() {

    lateinit var adapter: PropertyAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        val rv = findViewById<RecyclerView>(R.id.rvPropertyList)
        rv.layoutManager = LinearLayoutManager(this)

        val dummyList = listOf(
            PropertyModel("104007800031001", "Anwar Ganj", "Anwar Ganj", "Mohd. Arif Siddiqui", "7394961470")
        )

        adapter = PropertyAdaptor(dummyList) { selected ->
            val intent= Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }

        rv.adapter = adapter
    }
}
