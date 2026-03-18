package com.example.lda.houseTax.transationHistory

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivityDetailTransactionHistoryBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.utils.dataClass.TransactionItem
import com.example.lda.utils.downloadPdfAndReceipt.ReceiptPdfUtil

class DetailTransactionHistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailTransactionHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_detail_transaction_history)

        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }



        val data = intent.getParcelableExtra<TransactionItem>("data")

        if (data != null) {
            when (data.status) {
                "SUCCESS" -> {
                    binding.imgStatus.setImageResource(R.drawable.ic_success)
                    binding.tvStatus.text = "Payment Successful"
                    binding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.green))
                    binding.btnDownload.backgroundTintList = ContextCompat.getColorStateList(this, R.color.green)
                }
                "FAILED" -> {
                    binding.imgStatus.setImageResource(R.drawable.ic_failed)
                    binding.tvStatus.text = "Payment Failed"
                    binding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.red))
                    binding.btnDownload.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red)
                }
                else -> {
                    binding.imgStatus.setImageResource(R.drawable.pending_case)
                    binding.tvStatus.text = "Payment Pending"
                    binding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.pending))
                    binding.btnDownload.backgroundTintList = ContextCompat.getColorStateList(this, R.color.pending)
                }
            }

            binding.tvAmount.text = data.amount
            binding.tvDate.text = data.date
            binding.tvTxnId.text = data.txnId
            binding.tvBillNo.text = data.billNo
            binding.tvPropertyId.text = data.propertyId
            binding.tvFinancialYear.text=data.financialYear
            binding.tvPaymentMode.text=data.paymentMode
            binding.tvBankRef.text=data.bankRefNo
        }





        binding.btnDownload.setOnClickListener {
            if (data != null) {
                ReceiptPdfUtil.downloadReceipt(
                    this,
                    data
                )
            }
        }

        binding.btnShare.setOnClickListener {
            if (data != null) {
                ReceiptPdfUtil.shareReceipt(
                    this,
                    data
                )
            }
        }

    }
}