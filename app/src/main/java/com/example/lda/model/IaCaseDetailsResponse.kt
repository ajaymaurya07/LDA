package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class IaCaseDetailsResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("result")
	val result: List<IaDetailsResultItem?>? = null,

	@field:SerializedName("status_code")
	val statusCode: String? = null
)

data class IaDetailsResultItem(

	@field:SerializedName("next_hearing")
	val nextHearing: String? = null,

	@field:SerializedName("registration_number")
	val registrationNumber: String? = null,

	@field:SerializedName("respondent_name")
	val respondentName: String? = null,

	@field:SerializedName("filing_date")
	val filingDate: String? = null,

	@field:SerializedName("ia_status")
	val iaStatus: String? = null,

	@field:SerializedName("petitioner_name")
	val petitionerName: String? = null,

	@field:SerializedName("designation")
	val designation: String? = null,

	@field:SerializedName("cnr_number")
	val cnrNumber: String? = null,

	@field:SerializedName("classification")
	val classification: String? = null,

	@field:SerializedName("case_type")
	val caseType: String? = null,
	@field:SerializedName("bench_type")
	val benchType: String? = null,
	@field:SerializedName("court_name")
	val courtName: String? = null,
)
