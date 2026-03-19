package com.example.lda.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.databinding.ItemGrievanceListBinding
import com.example.lda.model.GrievanceDetails

class GrievanceAdapter(
    private var list: List<GrievanceDetails>,
    private val onTrackClick: (GrievanceDetails) -> Unit
) : RecyclerView.Adapter<GrievanceAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGrievanceListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGrievanceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvGrievanceId.text = "ID: ${item.grievanceNo}"
            tvCategory.text = "Category: ${item.categoryName}"
            tvSubCategory.text = "Sub Category: ${item.subcategoryName}"
            
            btnTrack.setOnClickListener {
                onTrackClick(item)
            }
            
            root.setOnClickListener {
                onTrackClick(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<GrievanceDetails>) {
        list = newList
        notifyDataSetChanged()
    }
}
