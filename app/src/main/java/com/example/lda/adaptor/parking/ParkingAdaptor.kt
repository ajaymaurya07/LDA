package com.example.lda.adaptor.parking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.formfragment.parking.Parking

class ParkingAdaptor(
    private val list: List<Parking>,
    private val onClick: (Parking) -> Unit
) : RecyclerView.Adapter<ParkingAdaptor.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvParkingName)
        val slots: TextView = itemView.findViewById(R.id.tvParkingSlots)
        val btnBook: Button = itemView.findViewById(R.id.btnBookNow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parking, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.slots.text = item.slots
        holder.btnBook.setOnClickListener { onClick(item) }
    }
}
