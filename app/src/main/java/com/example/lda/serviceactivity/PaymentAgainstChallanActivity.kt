package com.example.lda.serviceactivity

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

import androidx.fragment.app.Fragment
import com.example.lda.R
import com.example.lda.formfragment.interfacePart.FragmentChangeLister
import com.example.lda.formfragment.paymentAgainstChallan.AllotteDetails
import com.example.lda.formfragment.paymentAgainstChallan.FileDetails
import com.example.lda.formfragment.paymentAgainstChallan.PaymentType
import com.example.lda.formfragment.paymentAgainstChallan.PlotDetails

class PaymentAgainstChallanActivity : AppCompatActivity() , FragmentChangeLister {
    lateinit var fileDetails: CardView
    lateinit var plotDetails: CardView
    lateinit var allotteDetails: CardView
    lateinit var paymentType: CardView

    // https://online.dda.org.in/onlinepmt/Forms/ddaflatspmt.aspx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_payment_against_challan)


        initViews()
        setupBackButton()
        observeFragmentChanges()

        // Initial fragment
        setCurrentFragment(FileDetails(), addToBackStack = false)
        highlightCard(fileDetails)


    }

    private fun initViews() {
        fileDetails = findViewById(R.id.card1)
        plotDetails = findViewById(R.id.card2)
        allotteDetails = findViewById(R.id.card3)
        paymentType = findViewById(R.id.card4)
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
                is AllotteDetails -> highlightCard(allotteDetails)
                is FileDetails -> highlightCard(fileDetails)
                is PaymentType -> highlightCard(paymentType)
                is PlotDetails -> highlightCard(plotDetails)
            }
        }
    }

    private fun highlightCard(selectedCard: CardView) {
        val highlightColor = ContextCompat.getColor(this, R.color.secondary)
        val normalColor = ContextCompat.getColor(this, R.color.grayDiv)

        val cardViews = listOf(allotteDetails, fileDetails, paymentType,plotDetails)
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
            "allotteDetailsCardView" -> highlightCard(allotteDetails)
            "fileDetailsCardView" -> highlightCard(fileDetails)
            "paymentTypeCardView" -> highlightCard(paymentType)
            "plotDetailsCardView" -> highlightCard(plotDetails)
        }
    }
}