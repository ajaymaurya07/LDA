package com.example.lda.adaptor.mutation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.utils.dataClass.FormSummary


class MutationTrackStatus(
    private val items: List<FormSummary>
) : RecyclerView.Adapter<MutationTrackStatus.FormViewHolder>() {

    class FormViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val detailsContainer: LinearLayout = itemView.findViewById(R.id.detailsContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mutation_track_status, parent, false)
        return FormViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = items[position]

        holder.detailsContainer.removeAllViews()

        for (detail in item.details) {
            val textView = TextView(holder.itemView.context).apply {
                text = "• $detail"
                textSize = 14f
                setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                setPadding(0, 4, 0, 4)
            }
            holder.detailsContainer.addView(textView)
        }
    }

    override fun getItemCount(): Int = items.size
}
