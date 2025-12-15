package com.example.lda.houseTax

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.ActivityPropertyTaxBinding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.propertTaxAssessment.FirstScreenFragment
import com.example.lda.houseTax.propertTaxAssessment.ThirdScreenFragment
import com.example.lda.houseTax.propertTaxAssessment.SecondScreenFragment
import com.example.lda.houseTax.propertTaxAssessment.FourthScreenFragment
import com.example.lda.houseTax.viewmodel.SharedViewModel
import java.io.File
import java.io.FileOutputStream

class PropertyTaxAssessment : AppCompatActivity() {

    lateinit var binding: ActivityPropertyTaxBinding
    private var currentStep = 1
    private lateinit var viewModel: SharedViewModel
    var rentArea="0"
    var ownArea="0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_tax)

        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true
        )



        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        viewModel.rentArea.observe(this) {
            rentArea= it?:"0"
        }

        viewModel.ownArea.observe(this) {
            ownArea= it?:"0"
        }




        loadStepFragment()

        binding.navBack.setOnClickListener { handleBack() }
        binding.btnBack.setOnClickListener { handleBack() }

        binding.btnNext.setOnClickListener {
            if (currentStep < 4) {
                currentStep++
                loadStepFragment()
            } else {
                // ✅ LAST STEP ACTION
                generateTaxComparisonPdf()
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            handleBack()
        }
    }

    private fun handleBack() {
        if (currentStep > 1) {
            currentStep--
            loadStepFragment()
        } else {
            finish()
        }
    }

    private fun loadStepFragment() {
        val fragment = when (currentStep) {
            1 -> FirstScreenFragment()
            2 -> SecondScreenFragment()
            3 -> ThirdScreenFragment()
            4 -> FourthScreenFragment()
            else -> FirstScreenFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        updateUI()
    }

    private fun updateUI() {
        binding.tvTitle.text = "Property Tax Assessment (Step $currentStep of 4)"

        binding.btnBack.isEnabled = currentStep != 1

        binding.btnNext.text =
            if (currentStep == 4) "Download Tax Comparison PDF"
            else "Next"
    }

    // ============================
    // PDF GENERATE + OPEN
    // ============================
    private fun generateTaxComparisonPdf() {

        val context = this

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 12f

        var y = 40

        fun drawLine() {
            canvas.drawLine(30f, y.toFloat(), 565f, y.toFloat(), paint)
            y += 15
        }

        // =======================
        // TITLE
        // =======================
        paint.textSize = 16f
        paint.isFakeBoldText = true
        canvas.drawText("PROPERTY TAX CALCULATION & COMPARISON REPORT", 60f, y.toFloat(), paint)
        y += 25

        paint.textSize = 10f
        paint.isFakeBoldText = false
        canvas.drawText("Generated On: ${java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a").format(java.util.Date())}", 30f, y.toFloat(), paint)
        y += 20
        drawLine()

        // =======================
        // PROPERTY DETAILS
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("1. Property & Owner Details", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        val propertyDetails = listOf(
            "Property ID" to "10400780031001",
            "Owner Name" to "Ram Prasad",
            "Mobile No" to "9568254231"
        )

        propertyDetails.forEach {
            canvas.drawText("${it.first}: ${it.second}", 30f, y.toFloat(), paint)
            y += 14
        }

        drawLine()

        // =======================
        // AREA DETAILS
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("2. Area & Structure Details", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        val areaDetails = listOf(
            "Rent Area (Sq.Ft)" to rentArea,
            "Own Area (Sq.Ft)" to ownArea,
            "Total Area (Sq.Ft)" to rentArea.toDouble()+ownArea.toDouble(),
            "Area Rate" to "1.7",
            "Construction Year" to "2012",
            "Age of Structure" to "14 Years",
            "Type of Area" to "Covered"
        )

        areaDetails.forEach {
            canvas.drawText("${it.first}: ${it.second}", 30f, y.toFloat(), paint)
            y += 14
        }

        drawLine()

        // =======================
        // ARV CALCULATION
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("3. Assessment ARV", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        val arvDetails = listOf(
            "MRV Owner" to "206.40",
            "MRV Rented" to "103.20",
            "ARV Owner" to "2476.80",
            "ARV Rented" to "1238.40",
            "Depreciation" to "804.96",
            "Appreciation" to "154.80",
            "Final ARV (Owner)" to "1671.84",
            "Final ARV (Rent)" to "1398.20",
            "Total Assessment ARV (Own+Rent)" to "3070.04"
        )

        arvDetails.forEach {
            canvas.drawText("${it.first}: ₹ ${it.second}", 30f, y.toFloat(), paint)
            y += 14
        }

        drawLine()





        // =======================
        // TAX SUMMARY
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("4. Assessment Tax", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        canvas.drawText("Net Owner Tax: ₹ 250.76", 30f, y.toFloat(), paint)
        y += 14
        canvas.drawText("Net Rented Tax: ₹ 208.98", 30f, y.toFloat(), paint)
        y += 14

        paint.isFakeBoldText = true
        canvas.drawText("Total  Tax: ₹ 459.72", 30f, y.toFloat(), paint)
        paint.isFakeBoldText = false

        y += 20
        drawLine()






        // =======================
        // TAX SUMMARY
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("4. Current Payable ARV", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        canvas.drawText("Total Current Payable ARV: 3070.00", 30f, y.toFloat(), paint)


        y += 20
        drawLine()









        // =======================
        // TAX SUMMARY
        // =======================
        // 🔹 Heading (BOLD)
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("4. Current Payable Tax", 30f, y.toFloat(), paint)
        y += 20

// 🔹 Value (NORMAL / UNBOLD)
        paint.textSize = 12f
        paint.isFakeBoldText = false
        canvas.drawText("Total Current Tax: ₹ 460.00", 30f, y.toFloat(), paint)

        y += 20
        drawLine()








        // =======================
        // COMPARISON
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("5. ARV & Tax Comparison Statement", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        canvas.drawText("Assessment ARV: 3070.04", 30f, y.toFloat(), paint)
        y += 14
        canvas.drawText("Current Payable ARV: 3070.00", 30f, y.toFloat(), paint)
        y += 14
        canvas.drawText("Difference in ARV: 0.04", 30f, y.toFloat(), paint)
        y += 14
        canvas.drawText("Assessment Tax : ₹ 459.72", 30f, y.toFloat(), paint)
        y += 14
        canvas.drawText("Current Payable Tax : ₹ 460.00", 30f, y.toFloat(), paint)
        y += 14
        canvas.drawText("Difference in Tax: ₹ 0.28", 30f, y.toFloat(), paint)


        y += 25
        drawLine()


        // =======================
        // FOOTER
        // =======================
        paint.textSize = 9f
        canvas.drawText(
            "Generated by e-Nagarseva | This is a system generated document.",
            120f,
            820f,
            paint
        )

        pdfDocument.finishPage(page)

        // =======================
        // SAVE FILE
        // =======================
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "Property_Tax_Comparison_${System.currentTimeMillis()}.pdf"
        )

        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()

        Toast.makeText(context, "PDF Downloaded: ${file.name}", Toast.LENGTH_LONG).show()

        // Open PDF
        val uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        startActivity(intent)
    }



    private fun openPdf(file: File) {
        val uri = FileProvider.getUriForFile(
            this,
            "$packageName.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(intent)
    }
}

