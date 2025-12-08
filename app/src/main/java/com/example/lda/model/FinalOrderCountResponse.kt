package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class FinalOrderCountResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("result")
	val result: FinalOrderResult? = null,

	@field:SerializedName("status_code")
	val statusCode: String? = null
)

data class FinalOrderResult(

	@field:SerializedName("Settlement")
	val settlement: String? = null,

	@field:SerializedName("Compliance Order")
	val complianceOrder: String? = null,

	@field:SerializedName("Court Ruling")
	val courtRuling: String? = null,

	@field:SerializedName("Fine Penality")
	val finePenality: String? = null
)
