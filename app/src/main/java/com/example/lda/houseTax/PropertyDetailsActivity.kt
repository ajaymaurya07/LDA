package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.lda.model.Data
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson

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


        val json = intent.getStringExtra("property_data_json")
        val pid = intent.getStringExtra("pid")

        val data: Data? = json?.let { Gson().fromJson(it, Data::class.java) }

        val wardName= data?.propertyDetails?.wardName ?: ""
        val mohallaNane= data?.propertyDetails?.mohallaName ?: ""
        val ownerName= data?.ownerDetails?.ownerName ?: ""
        val mobileNumber= data?.ownerDetails?.mobileNo ?: ""



        val rv = findViewById<RecyclerView>(R.id.rvPropertyList)
        rv.layoutManager = LinearLayoutManager(this)

        val dummyList = listOf(
            PropertyModel(pid!!, wardName,mohallaNane,ownerName,mobileNumber)
        )

        adapter = PropertyAdaptor(dummyList) { selected ->
            val intent= Intent(this,PaymentActivity::class.java)
            intent.putExtra("property_data_json", json)
            intent.putExtra("pid",pid)
            startActivity(intent)
        }

        rv.adapter = adapter
    }
}
