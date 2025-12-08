package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class CaseStatusCountResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("status_code")
	val statusCode: String? = null
)

data class Result(

	@field:SerializedName("total_cases")
	val totalCases: Int? = null,

	@field:SerializedName("pending_cases")
	val pendingCases: Int? = null,

	@field:SerializedName("disposed_cases")
	val disposedCases: Int? = null,

)
