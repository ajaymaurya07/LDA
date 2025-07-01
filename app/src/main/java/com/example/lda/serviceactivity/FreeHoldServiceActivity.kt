package com.example.lda.serviceactivity

import android.os.Bundle
import android.widget.ImageView
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
import com.example.lda.formfragment.mutation.MutationReasonFragment

class FreeHoldServiceActivity : AppCompatActivity() {

    private lateinit var applicationDiv: CardView
    private lateinit var propertyDiv: CardView
    private lateinit var documentDiv: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_free_hold_service)

        applicationDiv=findViewById(R.id.application)
        propertyDiv=findViewById(R.id.property)
        documentDiv=findViewById(R.id.document)


        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
        val applicationDetails = ApplicationDetails()
        val propertyDetails=PropertyDetails()

        setCurrentFragment(applicationDetails)


        applicationDiv.setOnClickListener {
            setCurrentFragment(applicationDetails)
        }
        propertyDiv.setOnClickListener {
            setCurrentFragment(propertyDetails)
        }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }

        when (fragment) {
            is ApplicationDetails -> {
                applicationDiv.setCardBackgroundColor(ContextCompat.getColor(this, R.color.secondary))
                propertyDiv.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grayDiv))
                documentDiv.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grayDiv))
            }

            is PropertyDetails -> {
                propertyDiv.setCardBackgroundColor(ContextCompat.getColor(this, R.color.secondary))
                applicationDiv.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grayDiv))
                documentDiv.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grayDiv))
            }

            // You can add more cases here if you use the Document fragment later
        }
    }

}