package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class MohallaListResponse(

	@field:SerializedName("data")
	val data: List<MohallaItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)

data class MohallaItem(

	@field:SerializedName("ulbId")
	val ulbId: String? = null,

	@field:SerializedName("zoneId")
	val zoneId: String? = null,

	@field:SerializedName("mohallaId")
	val mohallaId: String? = null,

	@field:SerializedName("wardId")
	val wardId: String? = null,

	@field:SerializedName("mohallaName")
	val mohallaName: String? = null
)
