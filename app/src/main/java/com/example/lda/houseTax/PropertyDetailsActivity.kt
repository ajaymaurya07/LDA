package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.data.PropertyAdaptor
import com.example.lda.houseTax.data.PropertyModel
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PropertyDetailsViewmodel
import com.example.lda.houseTax.viewmodel.PropertyIdFromDb
import com.example.lda.utils.LoderHelper
import com.example.lda.utils.dataClass.PropertyDetailsRequest
import com.google.gson.Gson

class PropertyDetailsActivity : AppCompatActivity() {

    lateinit var adapter: PropertyAdaptor
    lateinit var propertyIdFromDbViewModel : PropertyIdFromDb
    lateinit var propertyDetailsViewmodel: PropertyDetailsViewmodel
    lateinit var preferenceManager: PreferenceManager
    lateinit var rv: RecyclerView
    private lateinit var loderHelper: LoderHelper
    var selectedPropertyId: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        propertyIdFromDbViewModel= ViewModelProvider(this)[PropertyIdFromDb::class.java]
        propertyDetailsViewmodel= ViewModelProvider(this)[PropertyDetailsViewmodel::class.java]
        loderHelper= LoderHelper(this)
        preferenceManager= PreferenceManager(this)


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

        propertyIdFromDbViewModel.loadProperties()


        rv = findViewById(R.id.rvPropertyList)
        rv.layoutManager = LinearLayoutManager(this)


        observer()

    }

    fun observer() {
        propertyIdFromDbViewModel.propertyList.observe(this) { list ->
            if (!list.isNullOrEmpty()) {
                val propertyList = list.map { entity ->
                    PropertyModel(
                        entity.propertyId,
                        ward = entity.ward,
                        mohalla = entity.mohalla,
                        ownerName = entity.ownerName,
                        mobile = entity.phoneNumber
                    )
                }
                adapter = PropertyAdaptor(propertyList) { selected ->
                    selectedPropertyId=selected.pid
                    propertyDetailsViewmodel.propertyDetailsRequest= PropertyDetailsRequest(propertyId = selected.pid)
                    propertyDetailsViewmodel.propertyDetailsData(preferenceManager.getLoginMobileNumber().toString())
                }
                rv.adapter = adapter
            }
        }

        propertyDetailsViewmodel.isLoading.observe(this){
            if (it){
                loderHelper.startLoadingDialog("Data is loading...")
            }else{
                loderHelper.dismissDialog()
            }
        }

        propertyDetailsViewmodel.dataList.observe(this) { response ->
            val json = Gson().toJson(response.data)
            if (response.success == true) {
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("property_data_json", json)
                intent.putExtra("pid", selectedPropertyId)
                startActivity(intent)
            }

        }
    }
}
