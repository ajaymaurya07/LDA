package com.example.lda.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AllCaseDetailsResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("result")
	val result: List<ResultCaseItem?>? = null,

	@field:SerializedName("status_code")
	val statusCode: String? = null
)

@Parcelize
data class ResultCaseItem(

	@field:SerializedName("next_hearing")
	val nextHearing: String? = null,

	@field:SerializedName("case_status")
	val caseStatus: String? = null,

	@field:SerializedName("registration_number")
	val registrationNumber: String? = null,

	@field:SerializedName("respondent_name")
	val respondentName: String? = null,

	@field:SerializedName("filing_date")
	val filingDate: String? = null,

	@field:SerializedName("petitioner_name")
	val petitionerName: String? = null,

	@field:SerializedName("designation")
	val designation: String? = null,

	@field:SerializedName("cnr_number")
	val cnrNumber: String? = null,

	@field:SerializedName("bench_type")
	val benchType: String? = null,

	@field:SerializedName("case_type")
	val caseType: String? = null,

	@field:SerializedName("court_name")
	val courtName: String? = null,


): Parcelable
