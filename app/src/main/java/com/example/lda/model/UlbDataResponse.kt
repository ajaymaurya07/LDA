package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class UlbDataResponse(

	@field:SerializedName("data")
	val data: List<UlbItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)

data class UlbItem(

	@field:SerializedName("districtId")
	val districtId: String? = null,

	@field:SerializedName("ulbType")
	val ulbType: String? = null,

	@field:SerializedName("districtName")
	val districtName: String? = null,

	@field:SerializedName("ulbId")
	val ulbId: String? = null,

	@field:SerializedName("ulbName")
	val ulbName: String? = null
){
	override fun toString(): String {
		return "${ulbName ?: ""} (${ulbType ?: ""})"
	}
}
