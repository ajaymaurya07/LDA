package com.example.lda.houseTax

import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.MainMenu
import com.example.lda.R
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.data.PaymentHistoryAdaptor
import com.example.lda.houseTax.data.PropertySelectAdaptor
import com.example.lda.model.CurrReceiptDetailsItem
import com.example.lda.model.Data
import com.example.lda.model.PrevReceiptDetailsItem
import com.example.lda.model.PropertyItem
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream

class PaymentHistoryActivity : AppCompatActivity() {
    lateinit var paymentHistoryAdaptor: PaymentHistoryAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_history)

        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )


        findViewById<ImageView>(R.id.navBack).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        val json = intent.getStringExtra("property_data_json")
        val data: Data? = json?.let { Gson().fromJson(it, Data::class.java) }



        val receiptList = data?.prevReceiptDetails?.filterNotNull() ?: emptyList()
        val currentReceiptList = data?.currReceiptDetails?.filterNotNull() ?: emptyList()

        val convertedPrevList = receiptList.map { prev ->
            CurrReceiptDetailsItem(
                receiptNo = prev.receiptNo,
                billNo = prev.billNo,
                receiptDate = prev.receiptDate,
                paymentMode = prev.paymentMode,
                paymentDate = prev.paymentDate,
                challanId = prev.challanId,
                chequeNo = prev.chequeNo,
                propertyTaxNetAmount = prev.propertyTaxNetAmount,
                propertyTaxPaidAmount = prev.propertyTaxPaidAmount,

            )
        }

        val finalList: List<CurrReceiptDetailsItem> =
            currentReceiptList + convertedPrevList





        val rv = findViewById<RecyclerView>(R.id.recyler_view_payment)
        rv.layoutManager = LinearLayoutManager(this)


        paymentHistoryAdaptor = PaymentHistoryAdaptor(finalList) { selected ->

//            val intent = Intent(this, MainMenu::class.java).apply {
//                putExtra("pid", selected.propertyId)
//            }
//
//            startActivity(intent)

        }
        rv.adapter = paymentHistoryAdaptor


    }

    private fun generateReceiptPdf() {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val titlePaint = Paint().apply {
            textSize = 18f
            isFakeBoldText = true
        }

        val textPaint = Paint().apply {
            textSize = 14f
        }

        var y = 60

        // ===== TITLE =====
        canvas.drawText("PAYMENT RECEIPT", 200f, y.toFloat(), titlePaint)
        y += 40

        // ===== DETAILS =====
        canvas.drawText("Receipt No: RCPT1023409", 40f, y.toFloat(), textPaint)
        y += 30

        canvas.drawText("Date: 10 Dec 2025, 04:45 PM", 40f, y.toFloat(), textPaint)
        y += 30

        canvas.drawText("Property ID: 104007700642001", 40f, y.toFloat(), textPaint)
        y += 30

        canvas.drawText("Payment Mode: UPI", 40f, y.toFloat(), textPaint)
        y += 30

        canvas.drawText("Amount Paid: ₹ 2188", 40f, y.toFloat(), textPaint)
        y += 40

        canvas.drawText("Status: PAID", 40f, y.toFloat(), textPaint)

        pdfDocument.finishPage(page)

        // ===== SAVE FILE =====
        val fileName = "Payment_Receipt_${System.currentTimeMillis()}.pdf"
        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, fileName)

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this, "Receipt downloaded", Toast.LENGTH_SHORT).show()
            openPdf(file)
        } catch (e: Exception) {
            Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
        } finally {
            pdfDocument.close()
        }
    }


    private fun openPdf(file: File) {
        val uri = FileProvider.getUriForFile(
            this,
            "$packageName.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        startActivity(Intent.createChooser(intent, "Open Receipt"))
    }




}
