package com.example.lda.houseTax

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.ActivityPropertyTaxBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.data.AreaAndStructureDetails
import com.example.lda.houseTax.data.PropertyDetails
import com.example.lda.houseTax.propertTaxAssessment.FirstScreenFragment
import com.example.lda.houseTax.propertTaxAssessment.ThirdScreenFragment
import com.example.lda.houseTax.propertTaxAssessment.SecondScreenFragment
import com.example.lda.houseTax.propertTaxAssessment.FourthScreenFragment
import com.example.lda.houseTax.utils.HouseTaxPdfGenerator
import com.example.lda.houseTax.utils.PropertyTaxCalculator
import com.example.lda.houseTax.viewmodel.SharedViewModel
import org.json.JSONArray


class PropertyTaxAssessment : AppCompatActivity() {

    lateinit var binding: ActivityPropertyTaxBinding
    private var currentStep = 1
    private lateinit var viewModel: SharedViewModel
    lateinit var rateArray: JSONArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_tax)

        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true
        )

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]



        loadStepFragment()

        binding.navBack.setOnClickListener { handleBack() }
        binding.btnBack.setOnClickListener { handleBack() }

        binding.btnNext.setOnClickListener {

            // Step validation
            if (!isStepValid(currentStep)) return@setOnClickListener

            if (currentStep < 4) {
                currentStep++
                loadStepFragment()


                if (currentStep == 4) {
                    calculateAndStoreResult()
                    calculatePropertyDetails()
                    calculateAreaAndStructure()
                }

            } else {
                // FINAL STEP → PDF
                val data = viewModel.calculationResult.value
                if (data == null) {
                    toast("Calculation data not available")
                    return@setOnClickListener
                }

                HouseTaxPdfGenerator(this).generate(data, viewModel.propertyDetails.value!!,viewModel.areaAndStructureDetails.value!!)
            }
        }



        onBackPressedDispatcher.addCallback(this) {
            handleBack()
        }


        loadRateJson(this)

        val rate = getBaseRateUsingLoop(
            wardNo = "2",
            constructionType = "KACHA",
            roadWidth = 3
        )
        viewModel.areaRate.value=rate

    }

    private fun calculateAreaAndStructure(){
        viewModel.areaAndStructureDetails.value= AreaAndStructureDetails(
            areaRate = viewModel.areaRate.value.toString(),
            constructionYear = viewModel.constructionYear.value.toString(),
            ageOfStructure = viewModel.ageOfConstruction.value.toString()
        )

    }

    private fun calculatePropertyDetails(){
        viewModel.propertyDetails.value= PropertyDetails(
            PropertyId = viewModel.propertyId.value!!,
            OwnerName = viewModel.ownerName.value!!,
            MobileNo = viewModel.mobileNo.value!!
        )
    }

    private fun calculateAndStoreResult() {

        val result = PropertyTaxCalculator.calculate(
            areaOwn = viewModel.ownArea.value!!.toDouble(),
            areaRent = viewModel.rentArea.value!!.toDouble(),
            rate = viewModel.areaRate.value!!,
            age = viewModel.age.value!!
        )

        viewModel.calculationResult.value = result

    }



    private fun isStepValid(step: Int): Boolean {
        return when (step) {

            1 -> {
                if (viewModel.roadWidth.value == null ||
                    viewModel.constructionType.value == null
                ) {
                    toast("Road Width & Construction Type are mandatory!")
                    false
                } else true
            }

            2 -> {
                if (viewModel.propertyType.value == null) {
                    toast("Property Type is mandatory!")
                    false
                } else true
            }

            3 -> {
                if (viewModel.rentArea.value.isNullOrEmpty() ||
                    viewModel.ownArea.value.isNullOrEmpty()
                ) {
                    toast("Rent Area and Own Area are mandatory!")
                    false
                } else true
            }

            else -> true
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }





    fun loadRateJson(context: Context) {
        val json = context.assets.open("rate_master.json")
            .bufferedReader().use { it.readText() }

        rateArray = JSONArray(json)
    }

    fun getBaseRateUsingLoop(
        wardNo: String,
        constructionType: String, // RCC / OTHER / KACHA / PLOT
        roadWidth: Int            // 3 => >24m, 2 => 12-24m, 1 => <12m
    ): Double {

        for (i in 0 until rateArray.length()) {

            val obj = rateArray.getJSONObject(i)

            // ✅ Ward match
            if (obj.getString("WardNo") == wardNo) {

                return when (constructionType) {

                    "RCC" -> when (roadWidth) {
                        3 -> obj.getDouble("Pakka Bhawan RCC or RBC >24m road")
                        2 -> obj.getDouble("Pakka Bhawan RCC or RBC 12 to 24m road")
                        else -> obj.getDouble("Pakka Bhawan RCC or RBC <12m road")
                    }

                    "OTHER" -> when (roadWidth) {
                        3 -> obj.getDouble("Other Pakka Bhawan >24m road")
                        2 -> obj.getDouble("Other Pakka Bhawan 12 to 24m road")
                        else -> obj.getDouble("Other Pakka Bhawan <12m road")
                    }

                    "KACHA" -> when (roadWidth) {
                        3 -> obj.getDouble("Kacha Bhawan >24m road")
                        2 -> obj.getDouble("Kacha Bhawan 12 to 24m road")
                        else -> obj.getDouble("Kacha Bhawan <12m road")
                    }

                    "PLOT" -> when (roadWidth) {
                        3 -> obj.getDouble("Residential Plot In Which Building is Not Constructed >24m road")
                        2 -> obj.getDouble("Residential Plot In Which Building is Not Constructed 12 to 24m road")
                        else -> obj.getDouble("Residential Plot In Which Building is Not Constructed <12m road")
                    }

                    else -> 0.0
                }
            }
        }

        return 0.0
    }



    private fun handleBack() {
        if (currentStep > 1) {
            currentStep--
            loadStepFragment()
        } else {
            finish()
        }
    }

    private fun loadStepFragment() {
        val fragment = when (currentStep) {
            1 -> FirstScreenFragment()
            2 -> SecondScreenFragment()
            3 -> ThirdScreenFragment()
            4 -> FourthScreenFragment()
            else -> FirstScreenFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        updateUI()
    }

    private fun updateUI() {
        binding.tvTitle.text = "Property Tax Assessment (Step $currentStep of 4)"

        binding.btnBack.isEnabled = currentStep != 1

        binding.btnNext.text =
            if (currentStep == 4) "Download Tax Comparison PDF"
            else "Next"
    }

}

