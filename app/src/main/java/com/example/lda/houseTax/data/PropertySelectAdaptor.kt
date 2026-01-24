package com.example.lda.houseTax.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.model.PropertyItem

class PropertySelectAdaptor(
    private val list: List<PropertyItem>,
    private val onDetailsClick: (PropertyItem) -> Unit
) : RecyclerView.Adapter<PropertySelectAdaptor.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val pid = view.findViewById<TextView>(R.id.tvPid)
        val owner = view.findViewById<TextView>(R.id.tvOwnerName)
        val father = view.findViewById<TextView>(R.id.tvFatherName)
        val house = view.findViewById<TextView>(R.id.tvHouseNo)
        val address = view.findViewById<TextView>(R.id.tvAddress)
        val btnViewDetails = view.findViewById<TextView>(R.id.btnViewDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_property_select_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.pid.text = "PID: ${item.propertyId}"
        holder.owner.text = "Owner Name: ${item.ownerName}"
        holder.father.text = "Father/Husband Name: ${item.fatherHusbandName}"
        holder.house.text = "House NUmber: ${item.houseNo}"
        holder.address.text = "Address: ${item.address}"

        holder.btnViewDetails.setOnClickListener {
            onDetailsClick(item)
        }
    }

    override fun getItemCount() = list.size
}