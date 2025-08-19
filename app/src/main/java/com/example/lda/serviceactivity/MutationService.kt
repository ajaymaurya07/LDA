package com.example.lda.serviceactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.lda.NoStatusFoundActivity
import com.example.lda.R
import com.example.lda.databinding.ActivityMutationServiceBinding

class MutationService : AppCompatActivity() {
    lateinit var binding:ActivityMutationServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_mutation_service)
        supportActionBar?.hide()

        val navText=intent.getStringExtra("text")
        binding.navText.text=navText

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.applyNow.setOnClickListener {
            if (navText=="Name Transfer/Mutation"){
                val intent= Intent(this,MutationServiceActivity::class.java)
                startActivity(intent)
            }
            else if(navText=="Freehold"){
                val intent= Intent(this,FreeHoldServiceActivity::class.java)
                startActivity(intent)
            }else if (navText=="Payment Against Challan"){
                val intent= Intent(this,PaymentAgainstChallanActivity::class.java)
                startActivity(intent)
            }
            else if (navText=="Martgage"){
                val intent= Intent(this,FreeHoldServiceActivity::class.java)
                intent.putExtra("text","Martgage")
                startActivity(intent)
            }


        }

        binding.trackStatus.setOnClickListener {
            val intent= Intent(this,NoStatusFoundActivity::class.java)
            startActivity(intent)
        }

    }
}