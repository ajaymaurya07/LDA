package com.example.lda.houseTax

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.lda.R
import com.example.lda.databinding.ActivityPropertySearchBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets

class PropertySearchActivity : AppCompatActivity() {

    lateinit var binding: ActivityPropertySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_search)
        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        highlightCard(binding.cardOwner)
        loadFormFragment(ByOwnerNameFragment())
        setupCardClicks()


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

