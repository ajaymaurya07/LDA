package com.example.lda.eCourtUi

import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lda.adaptor.CaseAdaptor
import com.example.lda.databinding.FragmentNotificationBinding
import com.example.lda.utils.dataClass.CaseItem
import java.io.File
import java.io.FileOutputStream

class NotificationFragment : Fragment() {

    lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        // Download Receipt click
        binding.btnDownloadReceipt.setOnClickListener {
            generateReceiptPdf()
        }

        return binding.root
    }

    // ================================
    // 📄 GENERATE PDF
    // ================================
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

        // ===== DUMMY DETAILS =====
        canvas.drawText("Receipt No: RCPT1023409", 40f, y.toFloat(), textPaint)
        y += 30

        canvas.drawText("Date: 10 Dec 2025, 04:45 PM", 40f, y.toFloat(), textPaint)
        y += 30

        canvas.drawText("Property ID: 104007700642001", 40f, y.toFloat(), textPaint)
        y += 30

        canvas.drawText("Payment Mode: UPI", 40f, y.toFloat(), textPaint)
        y += 30

        canvas.drawText("Amount Paid: ₹ 2188", 40f, y.toFloat(), textPaint)
        y += 30

        canvas.drawText("Status: PAID", 40f, y.toFloat(), textPaint)

        pdfDocument.finishPage(page)

        // ===== SAVE TO DOWNLOADS =====
        val fileName = "Payment_Receipt_${System.currentTimeMillis()}.pdf"
        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, fileName)

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(requireContext(), "Receipt downloaded", Toast.LENGTH_SHORT).show()
            openPdf(file)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
        } finally {
            pdfDocument.close()
        }
    }

    // ================================
    // 📂 OPEN PDF
    // ================================
    private fun openPdf(file: File) {
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        startActivity(Intent.createChooser(intent, "Open Receipt"))
    }
}
