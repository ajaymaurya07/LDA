package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.ActivityPropertySearchBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.SharedViewModel
import com.example.lda.utils.LoderHelper


class PropertySearchActivity : AppCompatActivity() {

    lateinit var binding: ActivityPropertySearchBinding
    lateinit var viewModel: SharedViewModel
    private lateinit var loderHelper: LoderHelper
    private lateinit var preferanceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_search)
        viewModel=ViewModelProvider(this)[SharedViewModel::class.java]
        loderHelper= LoderHelper(this)
        preferanceManager=PreferenceManager(this)

//        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        highlightCard(binding.cardOwner)
        loadFormFragment(ByOwnerNameFragment())
        setupCardClicks()


        viewModel.ulbData(preferanceManager.getLoginMobileNumber().toString())
        observerErrorMessage()
        observerLoader()
        observePropertyResult()


    }

    private fun observerErrorMessage(){
        viewModel.errorMessage.observe(this) {
            it?.let { msg ->
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun observerLoader(){
        viewModel.isLoading.observe(this){
            if (it){
                loderHelper.startLoadingDialog("Data is loading...")
            }else{
                loderHelper.dismissDialog()
            }
        }
    }


    private fun observePropertyResult() {

        viewModel.propertyList.observe(this) { list ->

            Log.d("TAG", "observePropertyResult: $list")

            if (list.isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "Property data not available",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }

            val ulbId = viewModel.selectedUlb.value?.ulbId.orEmpty()
            preferanceManager.saveUlbId(ulbId)
            val arvValue= list[0].totalArv
            preferanceManager.saveArvValue(arvValue.toString())

            val intent = Intent(this, SelectPropertyActivity::class.java)
            intent.putParcelableArrayListExtra("property_list", ArrayList(list))
            startActivity(intent)

        }
    }




    private fun setupCardClicks() {

        // By Owner Name
        binding.cardOwner.setOnClickListener {
            highlightCard(binding.cardOwner)
            loadFormFragment(ByOwnerNameFragment())
        }

        // By Property ID
        binding.cardProperty.setOnClickListener {
            highlightCard(binding.cardProperty)
            loadFormFragment(ByPropertyIdFragment())
        }

        // By House No (or Ward & House No)
        binding.cardHouse.setOnClickListener {
            highlightCard(binding.cardHouse)
            loadFormFragment(SearchByHouseNoFragment())
        }

        binding.cardMobileNo.setOnClickListener {
            highlightCard(binding.cardMobileNo)
            loadFormFragment(ByMobileNumberFragment())
        }



        binding.cardLocation.setOnClickListener {
            highlightCard(binding.cardLocation)
            loadFormFragment(LocationBasedFragment())

        }


        binding.cardMap.setOnClickListener {
            highlightCard(binding.cardMap)
            loadFormFragment(ByMapFragment())

        }

    }

    private fun highlightCard(selected: View) {
        val allCards = listOf(
            binding.cardOwner,
            binding.cardProperty,
            binding.cardHouse,
            binding.cardLocation,
            binding.cardMobileNo,
            binding.cardMap
        )

        // reset colors
        allCards.forEach {
            it.setBackgroundResource(R.drawable.filter_card_bg)
        }

        // highlight selected card
        selected.setBackgroundResource(R.drawable.filter_card_selected)
    }

    private fun loadFormFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.formContainer, fragment)
            .commit()
    }
}

