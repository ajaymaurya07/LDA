package com.example.lda.houseTax.data.receiptPdf

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object GenerateReceiptPdf {

    fun generateReceipt(
        context: Context,
        amount: String,
        txnId: String,
        dateTime: String,
        paymentMode: String,
        status: String
    ) {
        val pdfDocument = PdfDocument()
        val paint = Paint()
        val titlePaint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Title
        titlePaint.textSize = 20f
        titlePaint.isFakeBoldText = true
        titlePaint.textAlign = Paint.Align.CENTER
        canvas.drawText("PAYMENT RECEIPT", 297f, 60f, titlePaint)

        paint.textSize = 14f
        paint.textAlign = Paint.Align.LEFT

        var y = 120f
        val gap = 30f

        canvas.drawText("Amount Paid", 50f, y, paint)
        canvas.drawText("₹$amount", 350f, y, paint)

        y += gap
        canvas.drawText("Transaction ID", 50f, y, paint)
        canvas.drawText(txnId, 350f, y, paint)

        y += gap
        canvas.drawText("Date & Time", 50f, y, paint)
        canvas.drawText(dateTime, 350f, y, paint)

        y += gap
        canvas.drawText("Payment Mode", 50f, y, paint)
        canvas.drawText(paymentMode, 350f, y, paint)

        y += gap
        paint.isFakeBoldText = true
        canvas.drawText("Status", 50f, y, paint)
        canvas.drawText(status, 350f, y, paint)

        // Footer
        paint.isFakeBoldText = false
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(
            "Thank you for your payment",
            297f,
            760f,
            paint
        )

        pdfDocument.finishPage(page)






        // ===== SAVE TO DOWNLOADS =====
        val fileName = "Payment_Receipt_${System.currentTimeMillis()}.pdf"
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }

        val file = File(downloadsDir, fileName)

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(context, "Receipt downloaded", Toast.LENGTH_SHORT).show()
            openPdf(context, file)
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        } finally {
            pdfDocument.close()
        }


    }

    private fun openPdf(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(
            Intent.createChooser(intent, "Open Receipt")
        )
    }

}
