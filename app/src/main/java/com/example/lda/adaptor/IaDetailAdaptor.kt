package com.example.lda.adaptor


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lda.R
import com.example.lda.eCourtUi.HearingActivity
import com.example.lda.model.IaDetailsResultItem
import com.example.lda.utils.getLabelValueText

class IaDetailAdaptor(
    private val caseList: MutableList<IaDetailsResultItem?> = mutableListOf(),val context: Context
) : RecyclerView.Adapter<IaDetailAdaptor.CaseViewHolder>() {

    inner class CaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cnrNumber: TextView = itemView.findViewById(R.id.cnr_number)
        val registrationNumber: TextView = itemView.findViewById(R.id.registration_number)
        val designation: TextView = itemView.findViewById(R.id.designation)
        val petitionerName: TextView = itemView.findViewById(R.id.petitioner_name)
        val respondentName: TextView = itemView.findViewById(R.id.respondent_name)
        val caseType: TextView = itemView.findViewById(R.id.case_type)
        val benchType: TextView = itemView.findViewById(R.id.bench_type)
        val courtName: TextView = itemView.findViewById(R.id.court_name)
        val classification: TextView = itemView.findViewById(R.id.classification)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ia_details_layout, parent, false)
        return CaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        val case = caseList[position]

        holder.cnrNumber.text = getLabelValueText(context, "CNR Number : ", case?.cnrNumber)
        holder.registrationNumber.text = getLabelValueText(context, "Registration Number : ", case?.registrationNumber)
        holder.designation.text = getLabelValueText(context, "Designation : ", case?.designation)
        holder.petitionerName.text = getLabelValueText(context, "Petitioner Name : ", case?.petitionerName)
        holder.respondentName.text = getLabelValueText(context, "Respondent Name : ", case?.respondentName)
        holder.benchType.text = getLabelValueText(context, "Bench Type : ", case?.benchType)
        holder.caseType.text = getLabelValueText(context, "Case Type : ", case?.caseType)
        holder.courtName.text = getLabelValueText(context, "Court Name : ", case?.courtName)
        holder.classification.text = getLabelValueText(context, "Classification : ", case?.classification)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, HearingActivity::class.java).apply {
                putExtra("cnrNumber", case?.cnrNumber)
                putExtra("registrationNumber", case?.registrationNumber)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = caseList.size

    fun updateList(newList: List<IaDetailsResultItem?>?) {
        caseList.clear()
        newList?.let { caseList.addAll(it) }
        notifyDataSetChanged()
    }
}



