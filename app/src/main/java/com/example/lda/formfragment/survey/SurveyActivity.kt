package com.example.lda.formfragment.survey

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivitySurveyBinding

class SurveyActivity : AppCompatActivity() {
    lateinit var binding: ActivitySurveyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_survey)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        binding.constructionProgress.setOnClickListener {
            val intent = Intent(this, ConstructionProgressActivity::class.java)
            startActivity(intent)

        }
        binding.propertyOccupancySurvey.setOnClickListener {
            val intent = Intent(this, PropertyOccupancyActivity::class.java)
            startActivity(intent)
        }

        binding.publicSurvey.setOnClickListener {
            val intent = Intent(this, PublicSurveyActivity::class.java)
            startActivity(intent)
        }



    }
}