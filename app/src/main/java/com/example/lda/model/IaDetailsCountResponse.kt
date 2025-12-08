package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class IaDetailsCountResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("result")
	val result: IaResult? = null,

	@field:SerializedName("status_code")
	val statusCode: String? = null
)

data class IaResult(

	@field:SerializedName("designation_wise_case_count")
	val designationWiseCaseCount: DesignationWiseCaseCount? = null,

	@field:SerializedName("ia_details_count")
	val iaDetailsCount: IaDetailsCount? = null
)

data class IaDetailsCount(



	@field:SerializedName("ca_filed")
	val ca_filed: String? = null,

	@field:SerializedName("ca_not_filed")
	val ca_not_filed: String? = null,

	@field:SerializedName("rejoinder_filed")
	val rejoinder_filed: String? = null,

	@field:SerializedName("rejoinder_not_filed")
	val rejoinder_not_filed: String? = null,

	@field:SerializedName("Other")
	val other: String? = null
)

data class DesignationWiseCaseCount(

	@field:SerializedName("ADDITIONAL COMMISSIONER")
	val aDDITIONALCOMMISSIONER: String? = null,

	@field:SerializedName("Other")
	val other: String? = null,

	@field:SerializedName("Chairman")
	val Chairman: String? = null
)
