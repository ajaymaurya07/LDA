package com.example.lda.formfragment.survey

import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivityConstructionProgressBinding
import com.example.lda.utils.DatePickerUtils
import com.example.lda.utils.setupDropdown

class ConstructionProgressActivity : AppCompatActivity() {
    lateinit var binding: ActivityConstructionProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_construction_progress)

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        val propertyList = listOf("Project A", "Project B", "Project C", "Project D", "Project E", "Project F", "Project G", "Project H", "Project I", "Project J")
        setupDropdown(this, binding.projectName, propertyList)


        DatePickerUtils.attachDatePicker(this@ConstructionProgressActivity, binding.surveyDate)
        DatePickerUtils.attachDatePicker(this@ConstructionProgressActivity, binding.constructionStartDate)
        DatePickerUtils.attachDatePicker(this@ConstructionProgressActivity, binding.constructionExpectedCompletionDate)

        binding.progressSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.progressTextView.text = "Progress: $progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Optional: do something when user starts sliding
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Optional: do something when user stops sliding
            }
        })

    }
}