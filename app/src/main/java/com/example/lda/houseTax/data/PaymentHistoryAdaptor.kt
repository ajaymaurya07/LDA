package com.example.lda.houseTax.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.model.CurrReceiptDetailsItem

class PaymentHistoryAdaptor(
    private val list: List<CurrReceiptDetailsItem>,
    private val onDetailsClick: (CurrReceiptDetailsItem) -> Unit
) : RecyclerView.Adapter<PaymentHistoryAdaptor.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val receiptNo = view.findViewById<TextView>(R.id.tvReceiptNo)
        val billNo = view.findViewById<TextView>(R.id.billNo)
        val receiptDate = view.findViewById<TextView>(R.id.receiptDate)
        val paymentMode = view.findViewById<TextView>(R.id.paymentMode)
        val paymentDate = view.findViewById<TextView>(R.id.paymentDate)
        val challanId = view.findViewById<TextView>(R.id.challanId)
        val chequeNo = view.findViewById<TextView>(R.id.chequeNo)
        val propertyTaxAmount = view.findViewById<TextView>(R.id.propertyTaxNetAmount)
        val propertyTaxPaidAmount = view.findViewById<TextView>(R.id.propertyTaxPaidAmount)
        val propertyTaxDiscount = view.findViewById<TextView>(R.id.propertyTaxDiscount)
        val btnDownloadReceipt = view.findViewById<TextView>(R.id.btnDownloadReceipt)
        val status = view.findViewById<TextView>(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_payment_history, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        if (item.propertyTaxPaidAmount=="-"){
            holder.status.text="UNPAID"
        }

        holder.receiptNo.text = "Receipt Number: ${item.receiptNo}"
        holder.billNo.text = "Bill Number: ${item.billNo}"
        holder.receiptDate.text = "Receipt Date: ${item.receiptDate}"
        holder.paymentMode.text = "Payment Mode: ${item.paymentMode}"
        holder.paymentDate.text = "Payment Date: ${item.paymentDate}"
        holder.challanId.text = "Challan Id: ${item.challanId}"
        holder.chequeNo.text = "Cheque No: ${item.chequeNo}"
        holder.propertyTaxAmount.text = "Property Tax Net Amount: ${item.propertyTaxNetAmount}"
        holder.propertyTaxDiscount.text = "Property Tax Net Discount: ${item.propertyTaxDiscount}"
        holder.propertyTaxPaidAmount.text = "Property Tax Paid Amount: ${item.propertyTaxPaidAmount}"

        holder.btnDownloadReceipt.setOnClickListener {
            onDetailsClick(item)
        }
    }

    override fun getItemCount() = list.size
}