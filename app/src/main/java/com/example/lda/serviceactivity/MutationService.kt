package com.example.lda.serviceactivity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.lda.NoStatusFoundActivity
import com.example.lda.R
import com.example.lda.databinding.ActivityMutationServiceBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets

class MutationService : AppCompatActivity() {
    lateinit var binding:ActivityMutationServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_mutation_service)
        supportActionBar?.hide()

        val navText=intent.getStringExtra("text")

//        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )
        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
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
//            val intent= Intent(this,NoStatusFoundActivity::class.java)
//            startActivity(intent)
        }

    }
}