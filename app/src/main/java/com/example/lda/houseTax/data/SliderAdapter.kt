package com.example.lda.houseTax.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R

class SliderAdapter(private val list: List<SliderItem>) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon = itemView.findViewById<ImageView>(R.id.imgIcon)
        val title = itemView.findViewById<TextView>(R.id.tvTitle)
        val subtitle = itemView.findViewById<TextView>(R.id.tvSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slider, parent, false)
        return SliderViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val item = list[position]
        holder.icon.setImageResource(item.icon)
        holder.title.text = item.title
        holder.subtitle.text = item.subtitle
        holder.title.setTextColor(item.colorCode)
    }
}
