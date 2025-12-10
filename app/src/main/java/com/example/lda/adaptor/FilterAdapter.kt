package com.example.lda.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R

class FilterAdapter(
    private val list: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    inner class FilterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvFilter: TextView = view.findViewById(R.id.tvFilterName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filter_card, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.tvFilter.text = list[position]

        holder.view.setOnClickListener {
            onClick(list[position])
        }
    }

    override fun getItemCount() = list.size
}
