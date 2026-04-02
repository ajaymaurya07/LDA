package com.example.lda.houseTax

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivityPayBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import java.io.File
import java.io.FileOutputStream
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.example.lda.BaseActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PayActivity : BaseActivity() {
    lateinit var binding: ActivityPayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay)

        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        val pid = intent.getStringExtra("pid")
        val ownerName = intent.getStringExtra("name")
        val amount = intent.getStringExtra("amount") ?: ""


        binding.tvAmount.text="₹$amount"

        binding.paytmCard.setOnClickListener {
            temp(pid,ownerName,"paytm",amount)
        }
        binding.phoneCard.setOnClickListener {
            temp(pid,ownerName,"phone pay",amount)
        }
        binding.gpayCard.setOnClickListener {
            temp(pid,ownerName,"google pay",amount)
        }
        binding.cardPaymentCard.setOnClickListener {
            temp(pid,ownerName,"card payment",amount)
        }
        binding.netBankingCard.setOnClickListener {
            temp(pid,ownerName,"net banking",amount)
        }
    }


    fun temp(pid:String?,ownerName:String?,paymentMode:String,amount:String){

            // 1 Show loading dialog
            val dialog = ProgressDialog(this)
            dialog.setMessage("Processing Payment...\nPlease wait")
            dialog.setCancelable(false)
            dialog.show()

            Handler(Looper.getMainLooper()).postDelayed({

                dialog.setMessage("Confirming Transaction...")

                Handler(Looper.getMainLooper()).postDelayed({

                    dialog.dismiss()

                    // 2️ Generate Transaction ID
                    val txnId = "TXN" + (100000..999999).random()
                    val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
                    val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

                    // 3️ Generate Payment Receipt PDF
                    generatePaymentReceiptPDF(txnId, date, time,pid,ownerName,paymentMode,amount)

                    // 4️ Show success popup
                    AlertDialog.Builder(this)
                        .setTitle("Payment Successful")
                        .setMessage(
                            "Your payment has been successfully completed.\n\n" +
                                    "Transaction ID: $txnId\n" +
                                    "Date: $date\nTime: $time\n\n" +
                                    "Receipt has been downloaded."
                        )
                        .setPositiveButton("OK") { d, _ -> d.dismiss() }
                        .show()

                }, 1500)

            }, 2000)
    }


    private fun generatePaymentReceiptPDF(txnId: String, date: String, time: String,pid:String?,ownerName:String?,paymentMode:String ,amount: String) {

        val pdfDocument = PdfDocument()
        val paint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        paint.textSize = 18f
        paint.isFakeBoldText = true
        canvas.drawText("PROPERTY TAX PAYMENT RECEIPT", 130f, 50f, paint)

        paint.textSize = 14f
        paint.isFakeBoldText = false

        var y = 100

        // Receipt Content
        canvas.drawText("Receipt Date     : $date", 50f, y.toFloat(), paint)
        y += 30
        canvas.drawText("Payment Time     : $time", 50f, y.toFloat(), paint)
        y += 30
        canvas.drawText("Transaction ID   : $txnId", 50f, y.toFloat(), paint)
        y += 30
        canvas.drawText("Property ID      : $pid", 50f, y.toFloat(), paint)
        y += 30
        canvas.drawText("Owner Name       : $ownerName", 50f, y.toFloat(), paint)
        y += 30
        canvas.drawText("Payment Mode     : $paymentMode", 50f, y.toFloat(), paint)
        y += 30

        paint.isFakeBoldText = true
        canvas.drawText("Amount Paid      : ₹${amount}", 50f, y.toFloat(), paint)
        y += 40

        paint.textSize = 12f
        paint.isFakeBoldText = false
        canvas.drawText("Thank you for paying your property tax.", 50f, y.toFloat(), paint)

        pdfDocument.finishPage(page)

        // Save PDF
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, "PropertyTax_Receipt_${System.currentTimeMillis()}.pdf")

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this, "Receipt Downloaded", Toast.LENGTH_LONG).show()

            openPdf(file)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error generating receipt: ${e.message}", Toast.LENGTH_LONG).show()
        }

        pdfDocument.close()
    }


    private fun openPdf(file: File) {
        val uri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "No PDF Viewer Installed", Toast.LENGTH_LONG).show()
        }
    }
}