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
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.formfragment.WaterAddressDetailsFragment
import com.example.lda.formfragment.WaterApplicantDetailsFragment
import com.example.lda.formfragment.WaterConnectionDetailsFragment
import com.example.lda.formfragment.interfacePart.FragmentChangeLister

class WaterSewerageFormActivity : AppCompatActivity(), FragmentChangeLister {

    private lateinit var stepCard1: CardView
    private lateinit var stepCard2: CardView
    private lateinit var stepCard3: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_sewerage_form)
        supportActionBar?.hide()

        initViews()
        setupBackButton()
        observeFragmentChanges()

        // Initial fragment
        setCurrentFragment(WaterApplicantDetailsFragment(), addToBackStack = false)
        highlightCard(stepCard1)
    }

    private fun initViews() {
        stepCard1 = findViewById(R.id.stepCard1)
        stepCard2 = findViewById(R.id.stepCard2)
        stepCard3 = findViewById(R.id.stepCard3)
    }

    private fun setupBackButton() {
        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )
    }

    private fun observeFragmentChanges() {
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.formFragmentContainer)
            when (currentFragment) {
                is WaterApplicantDetailsFragment -> highlightCard(stepCard1)
                is WaterAddressDetailsFragment -> highlightCard(stepCard2)
                is WaterConnectionDetailsFragment -> highlightCard(stepCard3)
            }
        }
    }

    private fun highlightCard(selectedCard: CardView) {
        val highlightColor = ContextCompat.getColor(this, R.color.secondary)
        val normalColor = ContextCompat.getColor(this, R.color.grayDiv)

        val cardViews = listOf(stepCard1, stepCard2, stepCard3)
        cardViews.forEach {
            ViewCompat.setBackgroundTintList(it, ColorStateList.valueOf(normalColor))
        }
        ViewCompat.setBackgroundTintList(selectedCard, ColorStateList.valueOf(highlightColor))
    }

    private fun setCurrentFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.formFragmentContainer, fragment)
            if (addToBackStack) addToBackStack(null)
            commit()
        }
    }

    override fun replaceWith(fragment: Fragment, nextStep: String) {
        setCurrentFragment(fragment, addToBackStack = true)
        when (nextStep) {
            "addressStep" -> highlightCard(stepCard2)
            "connectionStep" -> highlightCard(stepCard3)
        }
    }
}
