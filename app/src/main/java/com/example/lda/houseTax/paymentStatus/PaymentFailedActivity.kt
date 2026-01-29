package com.example.lda.houseTax.paymentStatus

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.lda.MainMenu
import com.example.lda.R
import com.example.lda.databinding.ActivityPaymentFailedBinding
import com.example.lda.houseTax.data.receiptPdf.GenerateReceiptPdf

class PaymentFailedActivity : AppCompatActivity() {
    lateinit var binding: ActivityPaymentFailedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_payment_failed)

        
        // 🔹 Get data from Intent
        val amount = intent.getStringExtra("amount") ?: "-"
        val txnId = intent.getStringExtra("txnId") ?: "-"
        val dateTime = intent.getStringExtra("dateTime") ?: "-"
        val paymentMode = intent.getStringExtra("paymentMode") ?: "-"
        val status = intent.getStringExtra("status") ?: "-"

        // 🔹 Bind to UI
        binding.txtAmount.text = "₹$amount"
        binding.txtTxnId.text = txnId
        binding.txtDate.text = dateTime
        binding.txtMode.text = paymentMode
        binding.txtStatus.text = status

        binding.btnDownloadReceipt.setOnClickListener {
            GenerateReceiptPdf.generateReceipt(
                context = this,
                amount = amount,
                txnId = txnId,
                dateTime = dateTime,
                paymentMode = paymentMode,
                status = status
            )
        }


        binding.btnGoToDashboard.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Back + swipe + gesture disabled
                }
            }
        )

    }
}