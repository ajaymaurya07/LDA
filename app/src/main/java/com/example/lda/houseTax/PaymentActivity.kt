package com.example.lda.houseTax

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.lda.R
import com.example.lda.databinding.ActivityPayment2Binding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.paymentDetails.ArvHistoryActivity
import com.example.lda.model.Data
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayment2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_payment2)


        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )

        val json = intent.getStringExtra("property_data_json")
        val pid = intent.getStringExtra("pid")

        val data: Data? = json?.let { Gson().fromJson(it, Data::class.java) }


        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        var isExpanded = false

        binding.layoutHeader.setOnClickListener {
            isExpanded = !isExpanded

            if (isExpanded) {
                binding.layoutDetails.visibility = View.VISIBLE
                binding.imgArrow.animate().rotation(180f).setDuration(200).start()
            } else {
                binding.layoutDetails.visibility = View.GONE
                binding.imgArrow.animate().rotation(0f).setDuration(200).start()
            }
        }


        binding.btnAddGrievance.setOnClickListener {
            val intent = Intent(this, GravianceActivity::class.java)
            startActivity(intent)
        }


        binding.btnArvHistory.setOnClickListener {
            val intent = Intent(this, ArvHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.btnPaymentHistory.setOnClickListener {
            val intent = Intent(this, PaymentHistoryActivity::class.java)
            intent.putExtra("property_data_json", json)
            startActivity(intent)
        }


        binding.btnPayTax.setOnClickListener {




            // 1️⃣ Show loading dialog
            val dialog = ProgressDialog(this)
            dialog.setMessage("Processing Payment...\nPlease wait")
            dialog.setCancelable(false)
            dialog.show()

            Handler(Looper.getMainLooper()).postDelayed({

                dialog.setMessage("Confirming Transaction...")

                Handler(Looper.getMainLooper()).postDelayed({

                    dialog.dismiss()

                    // 2️⃣ Generate Transaction ID
                    val txnId = "TXN" + (100000..999999).random()
                    val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
                    val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

                    // 3️⃣ Generate Payment Receipt PDF
                    generatePaymentReceiptPDF(txnId, date, time)

                    // 4️⃣ Show success popup
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



        binding.btnPrintDetails.setOnClickListener {
            generatePropertyPdf()
        }





        binding.tvHeader.text="PID: $pid"
        val billDetails=data?.billDetails
        binding.tvTotalArv.text= billDetails?.houseCurrentTax
        binding.tvYearlyTax.text= billDetails?.houseTaxInterest
        binding.tvCurrentTax.text= billDetails?.houseTaxArrear
        binding.tvTotalTaxDue.text= billDetails?.houseTaxNetAmount
        binding.tvInterest.text= billDetails?.finYear
        binding.tvArrear.text= billDetails?.billDate


        val propertyDetails=data?.propertyDetails
        binding.tvPropertyId.text=pid
        binding.tvZone.text=propertyDetails?.zoneName
        binding.tvWardName.text=propertyDetails?.wardName
        binding.tvMohalla.text=propertyDetails?.mohallaName
        binding.tvOwnershipType.text=propertyDetails?.propertyType
        binding.tvHouseNo.text=propertyDetails?.houseNo
        binding.tvAddress.text=propertyDetails?.address
        binding.tvArea.text=propertyDetails?.totalArea
        binding.tvRoadWidth.text=propertyDetails?.propertyUseAs
        binding.tvYearlyTax2.text=propertyDetails?.chukNo

        val ownerDetails=data?.ownerDetails
        binding.tvOwnerName.text=ownerDetails?.ownerName
        binding.tvOwnerMobile.text=ownerDetails?.mobileNo
        binding.tvOwnerFatherName.text=ownerDetails?.fatherName

        
    }

    private fun generatePaymentReceiptPDF(txnId: String, date: String, time: String) {

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
        canvas.drawText("Property ID      : ${binding.tvPropertyId.text}", 50f, y.toFloat(), paint)
        y += 30
        canvas.drawText("Owner Name       : ${binding.tvOwnerName.text}", 50f, y.toFloat(), paint)
        y += 30
        canvas.drawText("Ward/Mohalla     : ${binding.tvWardName.text}", 50f, y.toFloat(), paint)
        y += 30

        paint.isFakeBoldText = true
        canvas.drawText("Amount Paid      : ₹${binding.tvTotalTaxDue.text}", 50f, y.toFloat(), paint)
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



    private fun generatePropertyPdf() {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 16f
        paint.isFakeBoldText = true

        var y = 50

        // ============================
        //   PROPERTY DETAILS SECTION
        // ============================
        canvas.drawText("PROPERTY DETAILS", 200f, y.toFloat(), paint)
        y += 40

        paint.isFakeBoldText = false

        canvas.drawText("Property ID: ${binding.tvPropertyId.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Reference No: ${binding.tvRefNo.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Assessment Date: ${binding.tvAssessmentDate.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Zone No: ${binding.tvZone.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Ward: ${binding.tvWardName.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Mohalla: ${binding.tvMohalla.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Owner Name: ${binding.tvOwnerName.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("House No: ${binding.tvHouseNo.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Address: ${binding.tvAddress.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Total Area: ${binding.tvArea.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Road Width: ${binding.tvRoadWidth.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Yearly Tax: ${binding.tvYearlyTax2.text}", 50f, y.toFloat(), paint)
        y += 35

        // Divider Line
        paint.strokeWidth = 2f
        canvas.drawLine(40f, y.toFloat(), 550f, y.toFloat(), paint)
        y += 40


        // ============================
        //        TAX SUMMARY
        // ============================
        paint.isFakeBoldText = true
        paint.textSize = 17f
        canvas.drawText("TAX SUMMARY", 220f, y.toFloat(), paint)
        y += 40

        paint.isFakeBoldText = false
        paint.textSize = 16f

        canvas.drawText("Total ARV: ${binding.tvTotalArv.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Yearly Tax: ${binding.tvYearlyTax.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Current Tax: ${binding.tvCurrentTax.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Arrear: ${binding.tvArrear.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Interest: ${binding.tvInterest.text}", 50f, y.toFloat(), paint)
        y += 35

        // Highlight total
        paint.isFakeBoldText = true
        paint.textSize = 18f
        paint.color = Color.BLUE

        canvas.drawText("Total Tax Due: ${binding.tvTotalTaxDue.text}", 50f, y.toFloat(), paint)
        y += 40

        // Finish page
        pdfDocument.finishPage(page)

        // ===== SAVE PDF TO DOWNLOAD FOLDER =====
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, "Property_Details_${System.currentTimeMillis()}.pdf")

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this, "PDF Downloaded: ${file.name}", Toast.LENGTH_LONG).show()

            openPdf(file) // auto open

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_LONG).show()
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