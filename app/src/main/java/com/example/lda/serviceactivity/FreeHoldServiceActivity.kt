package com.example.lda.serviceactivity

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.lda.R
import com.example.lda.formfragment.freehold.ApplicationDetails
import com.example.lda.formfragment.freehold.PropertyDetails
import com.example.lda.formfragment.interfacePart.FragmentChangeLister
import com.example.lda.formfragment.mutation.MutationReasonFragment
import com.example.lda.formfragment.mutation.PropertyDetailsMutation
import com.example.lda.formfragment.mutation.SupportingDocument

class FreeHoldServiceActivity : AppCompatActivity(), FragmentChangeLister {

    private lateinit var applicationDetails: CardView
    private lateinit var propertyDetails: CardView
    private lateinit var supportingDocument: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_free_hold_service)

        val navText=intent.getStringExtra("text")?:""
        if (navText=="Martgage"){
            findViewById<TextView>(R.id.nav_text).text=navText
        }

        initViews()
        setupBackButton()
        observeFragmentChanges()
        // Initial fragment
        setCurrentFragment(ApplicationDetails(), addToBackStack = false)
        highlightCard(applicationDetails)

    }

    private fun initViews() {
        applicationDetails=findViewById(R.id.card1)
        propertyDetails=findViewById(R.id.card2)
        supportingDocument=findViewById(R.id.card3)
    }

    private fun setupBackButton() {
        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeFragmentChanges() {
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
            when (currentFragment) {
                is ApplicationDetails -> highlightCard(applicationDetails)
                is PropertyDetails -> highlightCard(propertyDetails)
                is SupportingDocument -> highlightCard(supportingDocument)
            }
        }
    }

    private fun highlightCard(selectedCard: CardView) {
        val highlightColor = ContextCompat.getColor(this, R.color.secondary)
        val normalColor = ContextCompat.getColor(this, R.color.grayDiv)

        val cardViews = listOf(applicationDetails, propertyDetails, supportingDocument)
        cardViews.forEach {
            ViewCompat.setBackgroundTintList(it, ColorStateList.valueOf(normalColor))
        }
        ViewCompat.setBackgroundTintList(selectedCard, ColorStateList.valueOf(highlightColor))
    }

    private fun setCurrentFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            if (addToBackStack) addToBackStack(null)
            commit()
        }
    }

    override fun replaceWith(fragment: Fragment, nextCardView: String) {
        setCurrentFragment(fragment, addToBackStack = true)

        when (nextCardView) {
            "propertyDetailsCardView" -> highlightCard(propertyDetails)
            "supportingDocumentCardView" -> highlightCard(supportingDocument)
        }
    }

}