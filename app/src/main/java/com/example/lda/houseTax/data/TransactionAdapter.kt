package com.example.lda.houseTax.data

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.utils.dataClass.TransactionItem

class TransactionAdapter(private val list: List<TransactionItem>, private val onItemClick: (TransactionItem) -> Unit) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val tvDate: TextView = view.findViewById(R.id.tvDate)

        init {
            view.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(list[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvTitle.text = item.title
        holder.tvAmount.text = item.amount
        holder.tvStatus.text = item.status
        holder.tvDate.text = item.date

        when (item.status) {
            "SUCCESS" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#2E7D32"))
                holder.tvAmount.setTextColor(Color.parseColor("#2E7D32"))
            }
            "FAILED" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#D32F2F"))
                holder.tvAmount.setTextColor(Color.parseColor("#D32F2F"))
            }
            else -> {
                holder.tvStatus.setTextColor(Color.parseColor("#F57C00"))
                holder.tvAmount.setTextColor(Color.parseColor("#F57C00"))
            }
        }
    }
}