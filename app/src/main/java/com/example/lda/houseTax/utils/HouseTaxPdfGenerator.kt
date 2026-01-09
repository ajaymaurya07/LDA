package com.example.lda.houseTax.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.lda.houseTax.data.AreaAndStructureDetails
import com.example.lda.houseTax.data.PropertyDetails
import com.example.lda.houseTax.data.PropertyTaxCalculation
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HouseTaxPdfGenerator(
    private val context: Context
) {

    fun generate(data: PropertyTaxCalculation,
                 propertyData: PropertyDetails,
                 areaAndStructureData: AreaAndStructureDetails,
                 currentPayableArv:Double=3070.00,
                 currentPayableTax:Double=460.00) {

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
        }

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
        canvas.drawText(
            "PROPERTY TAX CALCULATION & COMPARISON REPORT",
            60f,
            y.toFloat(),
            paint
        )
        y += 25

        paint.textSize = 10f
        paint.isFakeBoldText = false
        canvas.drawText(
            "Generated On: ${
                SimpleDateFormat(
                    "dd MMM yyyy, hh:mm a",
                    Locale.getDefault()
                ).format(Date())
            }",
            30f,
            y.toFloat(),
            paint
        )

        y += 20
        drawLine()




        // =======================
        // Property & Owner details
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("Property & Owner details", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        val propertyDetails = listOf(
            "Property ID" to propertyData.PropertyId,
            "Owner Name" to propertyData.OwnerName,
            "Mobile Number" to propertyData.MobileNo,
        )

        propertyDetails.forEach {
            canvas.drawText(
                "${it.first}: ${it.second}",
                30f,
                y.toFloat(),
                paint
            )
            y += 14
        }

        y += 10
        drawLine()


        // =======================
        // Property & Owner details
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("Area & Structure details", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        val areaAndStructureDetails = listOf(
            "Area Rate" to areaAndStructureData.areaRate,
            "Construction Year" to areaAndStructureData.constructionYear,
            "Age Of Structure" to areaAndStructureData.ageOfStructure,
        )

        areaAndStructureDetails.forEach {
            canvas.drawText(
                "${it.first}: ${it.second}",
                30f,
                y.toFloat(),
                paint
            )
            y += 14
        }

        y += 10
        drawLine()



        // =======================
        // ARV CALCULATION
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("Assessment ARV", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        val arvDetails = listOf(
            "MRV Owner" to data.mrvOwner,
            "MRV Rented" to data.mrvRented,
            "ARV Owner" to data.arvOwner,
            "ARV Rented" to data.arvRented,
            "Depreciation" to data.depreciation,
            "Appreciation" to data.appreciation,
            "Final ARV (Owner)" to data.finalArvOwner,
            "Final ARV (Rent)" to data.finalArvRented,
            "Total Assessment ARV" to (data.finalArvOwner + data.finalArvRented)
        )

        arvDetails.forEach {
            canvas.drawText(
                "${it.first}: ${String.format("%.2f", it.second)}",
                30f,
                y.toFloat(),
                paint
            )
            y += 14
        }

        y += 10
        drawLine()




        // =======================
        // assessment TAX
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("Assessment Tax", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        canvas.drawText(
            "Net Owner Tax: ₹ ${String.format("%.2f", data.ownerTax)}",
            30f,
            y.toFloat(),
            paint
        )
        y += 14

        canvas.drawText(
            "Net Rented Tax: ₹ ${String.format("%.2f", data.rentedTax)}",
            30f,
            y.toFloat(),
            paint
        )
        y += 14

        paint.isFakeBoldText = false
        canvas.drawText(
            "Total Tax: ₹ ${String.format("%.2f", data.totalTax)}",
            30f,
            y.toFloat(),
            paint
        )

        y += 25
        drawLine()











        // =======================
        // current payable ARV
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("Current Payable ARV", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        val payableArv = listOf(
            "Total Current Payable ARV" to "$currentPayableArv",
        )

        payableArv.forEach {
            canvas.drawText(
                "${it.first}: ${ it.second}",
                30f,
                y.toFloat(),
                paint
            )
            y += 14
        }

        y += 10
        drawLine()







        // =======================
        // current payable Tax
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("Current Payable Tax", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        val payableTax = listOf(
            "Total Current Payable Tax" to "₹ $currentPayableTax",
        )

        payableTax.forEach {
            canvas.drawText(
                "${it.first}: ${ it.second}",
                30f,
                y.toFloat(),
                paint
            )
            y += 14
        }

        y += 10
        drawLine()







        // =======================
        // ARV & Tax Comparison Statement
        // =======================
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("ARV & Tax Comparison Statement", 30f, y.toFloat(), paint)
        y += 20

        paint.textSize = 11f
        paint.isFakeBoldText = false

        val comparisonStatement = listOf(
            "Total Assessment ARV (Own + Rent)" to
                    "₹ ${"%.2f".format(data.finalArvOwner + data.finalArvRented)}",

            "Current Total Payable ARV" to
                    "₹ ${"%.2f".format(currentPayableArv)}",

            "Total ARV Difference" to
                    "₹ ${"%.2f".format((data.finalArvOwner + data.finalArvRented) - currentPayableArv)}",

            "Total Assessment Tax (Own + Rent)" to
                    "₹ ${"%.2f".format(data.totalTax)}",

            "Current Total Payable Tax" to
                    "₹ ${"%.2f".format(currentPayableTax)}",

            "Total Tax Difference" to
                    "₹ ${"%.2f".format(data.totalTax - currentPayableTax)}"
        )


        comparisonStatement.forEach {
            canvas.drawText(
                "${it.first}: ${ it.second}",
                30f,
                y.toFloat(),
                paint
            )
            y += 14
        }

        y += 10
        drawLine()






        // FOOTER
        paint.textSize = 9f
        paint.isFakeBoldText = false
        canvas.drawText(
            "Generated by vdai biosec pvt. ltd | System generated document",
            120f,
            820f,
            paint
        )

        pdfDocument.finishPage(page)

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "Property_Tax_Comparison_${System.currentTimeMillis()}.pdf"
        )

        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()

        Toast.makeText(context, "PDF Downloaded: ${file.name}", Toast.LENGTH_LONG).show()

        openPdf(file)
    }

    private fun openPdf(file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(intent)
    }
}
