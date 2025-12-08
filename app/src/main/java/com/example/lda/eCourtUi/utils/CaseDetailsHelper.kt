package com.example.lda.eCourtUi.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.lda.R
import com.example.lda.eCourtUi.SummaryDashboard
import com.example.lda.model.CaseItemDetails
import com.example.lda.model.DesignationWiseCaseCount
import com.example.lda.model.FinalOrderResult
import com.example.lda.model.HistoryOfCaseHearingItem
import com.example.lda.model.IaDetailsCount
import com.example.lda.model.IaDetailsItem
import com.example.lda.model.InterimOrderItem
import com.google.gson.annotations.SerializedName

object CaseDetailsHelper {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }


    fun mapBasicDetails(caseDetails: CaseItemDetails): Map<String, String?> {
        return mapOf(
            "CNR Number" to caseDetails.caseDetails?.cNRNumber,
            "Case Type" to caseDetails.caseDetails?.caseType,
            "Filling Number" to caseDetails.caseDetails?.filingNumber,
            "Filing Date" to caseDetails.caseDetails?.filingDate,
            "Registration Number" to caseDetails.caseDetails?.registrationNumber,
            "Registration Date" to caseDetails.caseDetails?.registrationDate,
        )
    }

    fun mapHearingDetails(hearingDetail: CaseItemDetails): Map<String,String?> {
        return mapOf(
            "First Hearing Date" to hearingDetail.caseDetails?.firstHearingDate,
            "Next Hearing Date" to hearingDetail.caseDetails?.nextHearingDate,
            "Case Stage" to hearingDetail.caseDetails?.stageOfCase,
            "Coram" to hearingDetail.caseDetails?.coram,
            "Bench TYpe" to hearingDetail.caseDetails?.benchType,
            "Cause List Type" to hearingDetail.caseDetails?.causeListType,
        )
    }

    fun mapCourtDetails(courtDetail: CaseItemDetails): Map<String,String?> {
        return mapOf(
            "Court Name" to courtDetail.caseDetails?.courtName,
            "State" to courtDetail.caseDetails?.state,
            "District" to courtDetail.caseDetails?.district,
            "State Name" to courtDetail.caseDetails?.stateName,
            "District Name" to courtDetail.caseDetails?.districtName,
            "Judicial Branch" to courtDetail.caseDetails?.judicialBranch,
        )
    }

    fun mapLegalDetails(legalDetail: CaseItemDetails): Map<String,String?> {
        return mapOf(
            "Under Act" to legalDetail.caseDetails?.underAct,
            "Under Section" to legalDetail.caseDetails?.underSection,
        )
    }
    fun mapCaseStatus(caseStatus: CaseItemDetails): Map<String,String?> {
        return mapOf(
            "Case Status" to caseStatus.caseStatus?.caseStatus,
            "Date of Decision" to caseStatus.caseStatus?.dateOfDecision,
            "Disposition Name" to caseStatus.caseStatus?.dispositionName,
            "Objection Status" to caseStatus.caseStatus?.objectionStatus,
        )
    }

    fun mapCategoryDetails(categoryDetails: CaseItemDetails): Map<String,String?> {
        return mapOf(
            "Category" to categoryDetails.categoryDetails?.category,
            "Sub Category" to categoryDetails.categoryDetails?.subCategory,
        )
    }

    fun mapIaDetails(ia: IaDetailsItem): List<String> {
        return listOf(
            ia.iANumber ?: "",
            ia.party ?: "",
            ia.dateOfFiling ?: "",
            ia.nextDate ?: "",
            ia.iAStatus ?: "",
            ia.classification ?: ""
        )
    }

    fun mapHearingHistory(hearingHistory:HistoryOfCaseHearingItem): List<String> {
        return listOf(
            hearingHistory.causeListType ?: "",
            hearingHistory.judge ?: "",
            hearingHistory.businessOnDate ?: "",
            hearingHistory.hearingDate ?: "",
            hearingHistory.purposeOfListing ?: ""

        )
    }

    fun mapInterimOrder(interimOrder:InterimOrderItem): List<String> {
        return listOf(
            interimOrder.orderNumber ?: "",
            interimOrder.orderDate ?: "",
            interimOrder.orderDetailsURL ?: "",
        )
    }

    fun mapPetitionerAndAdvocate(petitionerAndAdvocate: CaseItemDetails): Map<String,String?> {
        return mapOf(
            "Petitioner Name" to petitionerAndAdvocate.petitionerAndAdvocate?.petitionerName,
            "Petitioner Advocate" to petitionerAndAdvocate.petitionerAndAdvocate?.petitionerAdvocates,
        )
    }
    fun mapRespondentAndAdvocate(petitionerAndAdvocate: CaseItemDetails): Map<String,String?> {
        return mapOf(
            "Respondent Name" to petitionerAndAdvocate.respondentAndAdvocate?.respondentName,
            "Respondent Advocate" to petitionerAndAdvocate.respondentAndAdvocate?.respondentAdvocates,
        )
    }
    fun mapSubOrdinateCourt(subOrdinateCourt: CaseItemDetails): Map<String,String?> {
        return mapOf(
            "Court Name" to subOrdinateCourt.subordinateCourtInformation?.courtName,
            "Case Number" to subOrdinateCourt.subordinateCourtInformation?.caseNumber,
            "Case Decision Date" to subOrdinateCourt.subordinateCourtInformation?.caseDecisionDate,
        )
    }


    fun populateBasicDetailsTable(
        context: Context,
        tableLayout: TableLayout,
        dataMap: Map<String, String?>
    ) {
        tableLayout.removeAllViews()

        val paddingDp = 8
        val paddingPx = (paddingDp * context.resources.displayMetrics.density).toInt()
        val textColor = ContextCompat.getColor(context, R.color.tertiary)

        dataMap.forEach { (key, value) ->
            val row = TableRow(context).apply {
                layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
            }

            // Key Column
            val keyText = TextView(context).apply {
                layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                )
                text = key
                setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
                background = ContextCompat.getDrawable(context, R.drawable.border)
                setTextColor(textColor)
                setTypeface(null, android.graphics.Typeface.BOLD)
                gravity = Gravity.START
                setLineSpacing(0f, 1.2f)
                isSingleLine = false
                ellipsize = null
            }

            // Value Column
            val valueText = TextView(context).apply {
                layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    2f
                )
                text = value ?: "-"
                setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
                background = ContextCompat.getDrawable(context, R.drawable.table_cell)
                setTextColor(textColor)
                gravity = Gravity.START
                setLineSpacing(0f, 1.2f)
                isSingleLine = false
                ellipsize = null
            }

            row.addView(keyText)
            row.addView(valueText)
            tableLayout.addView(row)
        }
    }



    fun populateTable(
        context: Context,
        tableLayout: TableLayout,
        tableData: List<List<String>>
    ) {
        if (tableData.isEmpty()) return

        val columnCount = tableData.first().size
        tableLayout.removeAllViews()

        val paddingDp = 8
        val paddingPx = (paddingDp * context.resources.displayMetrics.density).toInt()
        val headerTextColor = ContextCompat.getColor(context, R.color.black)
        val dataTextColor = ContextCompat.getColor(context, R.color.black)

        tableData.forEachIndexed { rowIndex, rowData ->
            val isHeaderRow = rowIndex == 0

            val tableRow = TableRow(context).apply {
                layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
            }

            for (i in 0 until columnCount) {
                val textView = TextView(context).apply {
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1f
                    )
                    text = rowData.getOrNull(i) ?: ""
                    gravity = Gravity.CENTER
                    textSize = 12f
                    setPadding(paddingPx, paddingPx, paddingPx, paddingPx)

                    // Apply style
                    if (isHeaderRow) {
                        background = ContextCompat.getDrawable(context, R.drawable.border)
                        setTextColor(headerTextColor)
                        setTypeface(null, android.graphics.Typeface.BOLD)
                    } else {
                        background = ContextCompat.getDrawable(context, R.drawable.table_cell)
                        setTextColor(dataTextColor)
                        setTypeface(null, android.graphics.Typeface.NORMAL)
                    }
                }
                tableRow.addView(textView)
            }

            tableLayout.addView(tableRow)
        }
    }


    fun populateInterimOrderTable(
        context: Context,
        tableLayout: TableLayout,
        tableData: List<List<String>>
    ) {
        if (tableData.isEmpty()) return

        val columnCount = tableData.first().size
        tableLayout.removeAllViews()

        val paddingDp = 8
        val paddingPx = (paddingDp * context.resources.displayMetrics.density).toInt()
        val headerTextColor = ContextCompat.getColor(context, R.color.black)
        val dataTextColor = ContextCompat.getColor(context, R.color.black)

        tableData.forEachIndexed { rowIndex, rowData ->
            val isHeaderRow = rowIndex == 0

            val tableRow = TableRow(context).apply {
                layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
            }

            for (i in 0 until columnCount) {
                val textView = TextView(context).apply {
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1f
                    )
                    text = rowData.getOrNull(i) ?: ""
                    gravity = Gravity.CENTER
                    textSize = 12f
                    setPadding(paddingPx, paddingPx, paddingPx, paddingPx)

                    // Apply style
                    if (isHeaderRow) {
                        background = ContextCompat.getDrawable(context, R.drawable.border)
                        setTextColor(headerTextColor)
                        setTypeface(null, android.graphics.Typeface.BOLD)
                    } else {
                        background = ContextCompat.getDrawable(context, R.drawable.table_cell)
                        setTextColor(dataTextColor)
                        setTypeface(null, android.graphics.Typeface.NORMAL)
                        if (i == 2) { // ✅ For URL column
                            val url = rowData.getOrNull(i)
                            if (!url.isNullOrEmpty()) {
                                text = "\uD83D\uDD17 Order Details" // 👈 Show this text instead of URL
                                setTextColor(ContextCompat.getColor(context, R.color.primary))

                                setOnClickListener {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Invalid URL", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                text = "N/A"
                            }
                        } else {
                            text = rowData.getOrNull(i) ?: ""
                        }
                    }
                }
                tableRow.addView(textView)
            }



            tableLayout.addView(tableRow)
        }
    }

    fun iaDetailsToMap(iaDetails: IaDetailsCount): Map<String, String> {
        val result = mutableMapOf<String, String>()

        iaDetails::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            val value = field.get(iaDetails) as? String
            if (!value.isNullOrEmpty()) {
                // Get @SerializedName value if present, else use field name
                val serializedName = field.getAnnotation(SerializedName::class.java)?.value ?: field.name
                result[serializedName] = value
            }
        }
        return result
    }



    fun designationDetailMap(iaDetails: DesignationWiseCaseCount): Map<String, String> {
        val result = mutableMapOf<String, String>()

        iaDetails::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            val value = field.get(iaDetails) as? String
            if (!value.isNullOrEmpty()) {
                // Get @SerializedName value if present, else use field name
                val serializedName = field.getAnnotation(SerializedName::class.java)?.value ?: field.name
                result[serializedName] = value
            }
        }
        return result
    }

    fun finalOrderDetailMap(finalOrder: FinalOrderResult): Map<String, String> {
        val result = mutableMapOf<String, String>()

        finalOrder::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            val value = field.get(finalOrder) as? String
            if (!value.isNullOrEmpty()) {
                // Get @SerializedName value if present, else use field name
                val serializedName = field.getAnnotation(SerializedName::class.java)?.value ?: field.name
                result[serializedName] = value
            }
        }
        return result
    }



    fun populateIaDetails(context: Context, parentLayout: LinearLayout, iaDetails: Map<String, String>) {
        parentLayout.removeAllViews() // Clear old data if any

        for ((key, value) in iaDetails) {
            val itemLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(8, 8, 8, 8)
                gravity = Gravity.CENTER_VERTICAL
                setBackgroundResource(R.drawable.bg_person_apperance_selector)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 0, 0, 8)
                layoutParams = params
            }

            val titleView = TextView(context).apply {
                text = key
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                textSize = 14f
                setTextColor(Color.parseColor("#212121"))
                typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)
            }

            val countView = TextView(context).apply {
                text = value
                setPadding(16, 8, 16, 8)
                setBackgroundResource(R.color.secondary)
                setTextColor(Color.WHITE)
                textSize = 14f
                typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
            }

            // Add click listener here
            itemLayout.setOnClickListener {
                val intent = Intent(context, SummaryDashboard::class.java)
                intent.putExtra("IA_KEY", key)        // pass IA name
                intent.putExtra("title", key)        // pass IA name
                context.startActivity(intent)
            }

            itemLayout.addView(titleView)
            itemLayout.addView(countView)
            parentLayout.addView(itemLayout)
        }
    }


    fun populateFinalOrderDetails(context: Context, parentLayout: LinearLayout, iaDetails: Map<String, String>,time:String) {
        parentLayout.removeAllViews() // Clear old data if any

        for ((key, value) in iaDetails) {
            val itemLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(8, 8, 8, 8)
                gravity = Gravity.CENTER_VERTICAL
                setBackgroundResource(R.drawable.bg_person_apperance_selector)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 0, 0, 8)
                layoutParams = params
            }

            val titleView = TextView(context).apply {
                text = key
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                textSize = 14f
                setTextColor(Color.parseColor("#212121"))
                typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)
            }

            val countView = TextView(context).apply {
                text = value
                setPadding(16, 8, 16, 8)
                setBackgroundResource(R.color.secondary)
                setTextColor(Color.WHITE)
                textSize = 14f
                typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
            }

            // Add click listener here
            itemLayout.setOnClickListener {
                val intent = Intent(context, SummaryDashboard::class.java)
                intent.putExtra("final_order_tab", key)
                intent.putExtra("title", key)
                intent.putExtra("time", time)
                context.startActivity(intent)
            }

            itemLayout.addView(titleView)
            itemLayout.addView(countView)
            parentLayout.addView(itemLayout)
        }
    }

}
