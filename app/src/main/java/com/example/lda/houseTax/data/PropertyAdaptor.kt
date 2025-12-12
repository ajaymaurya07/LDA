package com.example.lda.houseTax.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R

class PropertyAdaptor(
    private val list: List<PropertyModel>,
    private val onDetailsClick: (PropertyModel) -> Unit
) : RecyclerView.Adapter<PropertyAdaptor.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val pid = view.findViewById<TextView>(R.id.tvPid)
        val owner = view.findViewById<TextView>(R.id.tvOwnerName)
        val ward = view.findViewById<TextView>(R.id.tvWard)
        val mohalla = view.findViewById<TextView>(R.id.tvMohalla)
        val mobile = view.findViewById<TextView>(R.id.tvMobile)
        val btnViewDetails = view.findViewById<TextView>(R.id.btnViewDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_property_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.pid.text = "PID: ${item.pid}"
        holder.owner.text = "Owner: ${item.ownerName}"
        holder.mobile.text = "Mobile: ${item.mobile}"
        holder.ward.text = "Ward: ${item.ward}"
        holder.mohalla.text = "Mohalla: ${item.mohalla}"

        holder.btnViewDetails.setOnClickListener {
            onDetailsClick(item)
        }
    }

    override fun getItemCount() = list.size
}
