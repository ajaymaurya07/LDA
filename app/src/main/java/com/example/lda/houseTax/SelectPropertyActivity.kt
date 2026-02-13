package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.MainMenu
import com.example.lda.R
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.data.PropertySelectAdaptor
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PropertyDetailsViewmodel
import com.example.lda.houseTax.viewmodel.SharedViewModel
import com.example.lda.model.PropertyItem
import com.example.lda.utils.LoderHelper
import com.example.lda.utils.dataClass.PropertyDetailsRequest

class SelectPropertyActivity : AppCompatActivity() {
    lateinit var adapter: PropertySelectAdaptor
    private lateinit var preferenceManager: PreferenceManager
    lateinit var propertyDetailsViewmodel: PropertyDetailsViewmodel
    private lateinit var loderHelper: LoderHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_property)
        propertyDetailsViewmodel=ViewModelProvider(this)[PropertyDetailsViewmodel::class.java]
        loderHelper= LoderHelper(this)


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
            selected.propertyId?.let {
                preferenceManager.savePropertyId(it)
                propertyDetailsViewmodel.propertyDetailsRequest= PropertyDetailsRequest(propertyId = it)
                propertyDetailsViewmodel.propertyDetailsData()
            }
        }

        rv.adapter = adapter


        observerLoader()

    }

    private fun observerLoader(){
        propertyDetailsViewmodel.isLoading.observe(this){
            if (it){
                loderHelper.startLoadingDialog("Data is loading...")
            }else{
                loderHelper.dismissDialog()
            }
        }

        propertyDetailsViewmodel.dataList.observe(this) { response ->
            val mobileNo = response.data?.ownerDetails?.mobileNo
            if (response.success == true && !mobileNo.isNullOrBlank()) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("phone_no", mobileNo)
                startActivity(intent)
            }
            else if (response.success == true && mobileNo.isNullOrBlank()) {
                Toast.makeText(this, "Mobile number not available this property.", Toast.LENGTH_LONG).show()
            }
            else if (response.success != true) {
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            }
        }

    }
}