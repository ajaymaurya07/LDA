package com.example.lda.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.utils.dataClass.ParkingHistory

class ParkingHistoryAdaptor(private val parkingList: List<ParkingHistory>) :
    RecyclerView.Adapter<ParkingHistoryAdaptor.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val location: TextView = itemView.findViewById(R.id.parkingLocation)
        val date: TextView = itemView.findViewById(R.id.parkingDate)
        val duration: TextView = itemView.findViewById(R.id.parkingDuration)
        val amount: TextView = itemView.findViewById(R.id.parkingAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parking_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = parkingList[position]
        holder.location.text = item.location
        holder.date.text = "Date: ${item.date}"
        holder.duration.text = "Duration: ${item.duration}"
        holder.amount.text = "Amount: ${item.amount}"
    }

    override fun getItemCount(): Int = parkingList.size
}
