package com.example.lda.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.databinding.ItemGrievanceListBinding
import com.example.lda.model.GrievanceStatusData

class GrievanceAdapter(
    private var list: List<GrievanceStatusData>,
    private val onTrackClick: (GrievanceStatusData) -> Unit
) : RecyclerView.Adapter<GrievanceAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGrievanceListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGrievanceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvGrievanceId.text = "ID: ${item.complaintId}"
            tvCategory.text = "Category: ${item.categoryName}"
            tvSubCategory.text = "Sub Category: ${item.subCategoryName}"
            
            btnTrack.setOnClickListener {
                onTrackClick(item)
            }
            
            root.setOnClickListener {
                onTrackClick(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<GrievanceStatusData>) {
        list = newList
        notifyDataSetChanged()
    }
}