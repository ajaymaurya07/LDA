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
import com.example.lda.model.RecentInterimResultItem
import com.example.lda.utils.getLabelValueText

class RecentInterimOrderAdaptor(
    private val caseList: MutableList<RecentInterimResultItem?>,val context: Context
) : RecyclerView.Adapter<RecentInterimOrderAdaptor.CaseViewHolder>() {

    inner class CaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cnrNumber: TextView = itemView.findViewById(R.id.cnr_number)
        val registrationNumber: TextView = itemView.findViewById(R.id.registration_number)
        val designation: TextView = itemView.findViewById(R.id.designation)
        val petitionerName: TextView = itemView.findViewById(R.id.petitioner_name)
        val respondentName: TextView = itemView.findViewById(R.id.respondent_name)
        val interimOrderAction: TextView = itemView.findViewById(R.id.interim_order_action)
        val interimOrderNotes: TextView = itemView.findViewById(R.id.interim_order_notes)
        val interimOrderDeadline: TextView = itemView.findViewById(R.id.interim_order_deadline)
        val benchType: TextView = itemView.findViewById(R.id.bench_type)
        val caseType: TextView = itemView.findViewById(R.id.case_type)
        val courtName: TextView = itemView.findViewById(R.id.court_name)
        val interimOrderDate: TextView = itemView.findViewById(R.id.interim_order_date)
        val nextHearing: TextView = itemView.findViewById(R.id.next_hearing)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.interim_order_layout, parent, false)
        return CaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        val case = caseList[position]


        holder.cnrNumber.text = getLabelValueText(context, "CNR Number : ", case?.cnrNumber)
        holder.registrationNumber.text = getLabelValueText(context, "Registration Number : ", case?.registrationNumber)
        holder.designation.text = getLabelValueText(context, "Designation : ", case?.designation)
        holder.petitionerName.text = getLabelValueText(context, "Petitioner Name : ", case?.petitionerName)
        holder.respondentName.text = getLabelValueText(context, "Respondent Name : ", case?.respondentName)
        holder.interimOrderAction.text = getLabelValueText(context, "Interim Order Action : ", case?.interimAction)
        holder.interimOrderNotes.text = getLabelValueText(context, "Interim Order Notes : ", case?.interimNotes)
        holder.interimOrderDeadline.text = getLabelValueText(context, "Interim Order Deadline : ", case?.interimCalculatedDeadline)
        holder.benchType.text = getLabelValueText(context, "Bench Type : ", case?.benchType)
        holder.caseType.text = getLabelValueText(context, "Case Type : ", case?.caseType)
        holder.courtName.text = getLabelValueText(context, "Court Name : ", case?.courtName)
        holder.interimOrderDate.text = getLabelValueText(context, "Interim Order Date : ", case?.interimOrderDate)
        holder.nextHearing.text = getLabelValueText(context, "Next Hearing : ", case?.nextHearing)


        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, HearingActivity::class.java)
            intent.putExtra("cnrNumber", case?.cnrNumber)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = caseList.size

    fun updateList(newList: List<RecentInterimResultItem?>?) {
        caseList.clear()
        if (newList != null) {
            caseList.addAll(newList)
        }
        notifyDataSetChanged()
    }
}


