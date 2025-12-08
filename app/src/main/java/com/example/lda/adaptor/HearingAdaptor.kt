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
import com.example.lda.model.NextHearingResult
import com.example.lda.utils.getLabelValueText

class HearingAdaptor(
    private val caseList: MutableList<NextHearingResult?>,val context: Context
) : RecyclerView.Adapter<HearingAdaptor.CaseViewHolder>() {

    inner class CaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cnrNumber: TextView = itemView.findViewById(R.id.cnr_number)
        val registrationNumber: TextView = itemView.findViewById(R.id.registration_number)
        val designation: TextView = itemView.findViewById(R.id.designation)
        val petitionerName: TextView = itemView.findViewById(R.id.petitioner_name)
        val respondentName: TextView = itemView.findViewById(R.id.respondent_name)
        val nextHearing: TextView = itemView.findViewById(R.id.next_hearing)
        val hearingCount: TextView = itemView.findViewById(R.id.hearing_count)
        val benchType: TextView = itemView.findViewById(R.id.bench_type)
        val courtName: TextView = itemView.findViewById(R.id.court_name)
        val caseType: TextView = itemView.findViewById(R.id.case_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hearing_layout, parent, false)
        return CaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        val case = caseList[position]
        // Using reusable getLabelValueText for consistent style
        holder.cnrNumber.text = getLabelValueText(context, "CNR Number : ", case?.cnrNumber)
        holder.registrationNumber.text = getLabelValueText(context, "Registration Number : ", case?.registrationNumber)
        holder.designation.text = getLabelValueText(context, "Designation : ", case?.designation)
        holder.petitionerName.text = getLabelValueText(context, "Petitioner Name : ", case?.petitionerName)
        holder.respondentName.text = getLabelValueText(context, "Respondent Name : ", case?.respondentName)
        holder.nextHearing.text = getLabelValueText(context, "Next Hearing : ", case?.nextHearing)
        holder.hearingCount.text = getLabelValueText(context, "Hearing Number : ", case?.hearingCount)
        holder.benchType.text = getLabelValueText(context, "Bench Type : ", case?.benchType)
        holder.courtName.text = getLabelValueText(context, "Court Name : ", case?.courtName)
        holder.caseType.text = getLabelValueText(context, "Case Type : ", case?.caseType)



        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, HearingActivity::class.java)
            intent.putExtra("cnrNumber", case?.cnrNumber)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = caseList.size

    fun updateList(newList: List<NextHearingResult?>?) {
        caseList.clear()
        if (newList != null) {
            caseList.addAll(newList)
        }
        notifyDataSetChanged()
    }
}


