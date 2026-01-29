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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.lda.MainMenu
import com.example.lda.R
import com.example.lda.databinding.ActivityPaymentFailedBinding
import com.example.lda.databinding.ActivityPaymentPendingBinding
import com.example.lda.houseTax.data.receiptPdf.GenerateReceiptPdf
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.model.TransactionsDetails
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PaymentPendingActivity : AppCompatActivity() {
    lateinit var binding: ActivityPaymentPendingBinding
    private var pollingJob: Job? = null
    private var retryCount = 0
    private val MAX_RETRY = 6
    lateinit var viewModel:PaymentViewModel
    private lateinit var preferenceManager: PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_payment_pending)
        viewModel= ViewModelProvider(this)[PaymentViewModel::class.java]
        preferenceManager= PreferenceManager(this)


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


        val mobileTransactionId = preferenceManager.getMobileTransactionId()
        if (mobileTransactionId != null) {
            startPaymentStatusPolling(mobileTransactionId)
        }

    }



    override fun onDestroy() {
        stopPolling()
        super.onDestroy()
    }



    private fun navigateToSuccess(response: TransactionsDetails) {

        val intent = Intent(this, PaymentSuccessActivity::class.java).apply {
            putExtra("amount", response.netPayable)
            putExtra("txnId", response.txnid)
            putExtra("dateTime", response.mobileTransactionTimestamp)
            putExtra("paymentMode", response.paymentMode.toString())
            putExtra("status", "SUCCESS")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        startActivity(intent)
    }


    private fun navigateToFailed(response: TransactionsDetails) {

        val intent = Intent(this, PaymentFailedActivity::class.java).apply {
            putExtra("amount", response.netPayable)
            putExtra("txnId", response.txnid)
            putExtra("dateTime", response.mobileTransactionTimestamp)
            putExtra("paymentMode", response.paymentMode.toString())
            putExtra("status", "FAILED")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        startActivity(intent)
    }








    private fun startPaymentStatusPolling(txnId: String) {

        pollingJob = lifecycleScope.launch {

            while (isActive && retryCount < MAX_RETRY) {

                delay(10_000) // 10 seconds

                retryCount++

                checkPaymentStatus(txnId)
            }
        }
    }


    private fun checkPaymentStatus(txnId: String) {

        viewModel.transactionDetails(txnId)

        viewModel.transactionDetails.observe(this) { response ->

            if (response.status == true) {

                when (response.data?.paymentStatus) {

                    "SUCCESS" -> {
                        stopPolling()
                        navigateToSuccess(response.data)
                    }

                    "FAILED" -> {
                        stopPolling()
                        navigateToFailed(response.data)
                    }

                    "PENDING" -> {
                        // Do nothing → keep polling
                    }
                }
            }
        }
    }


    private fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }



}