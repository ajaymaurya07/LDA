package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.MainMenu
import com.example.lda.R
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.data.PropertySelectAdaptor
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.SharedViewModel
import com.example.lda.model.PropertyItem

class SelectPropertyActivity : AppCompatActivity() {
    lateinit var viewModel: SharedViewModel
    lateinit var adapter: PropertySelectAdaptor
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_property)
        viewModel=ViewModelProvider(this)[SharedViewModel::class.java]


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


        preferenceManager = PreferenceManager(this)



        val list = intent.getParcelableArrayListExtra<PropertyItem>("property_list")!!



        val rv = findViewById<RecyclerView>(R.id.rvPropertyList)
        rv.layoutManager = LinearLayoutManager(this)


        adapter = PropertySelectAdaptor(list) { selected ->
            selected.propertyId?.let { preferenceManager.savePropertyId(it) }
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finishAffinity()
        }

        rv.adapter = adapter

    }
}