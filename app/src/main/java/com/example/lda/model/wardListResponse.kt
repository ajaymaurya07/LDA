package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class WardItem(

	@field:SerializedName("wardName")
	val wardName: String? = null,

	@field:SerializedName("ulbId")
	val ulbId: String? = null,

	@field:SerializedName("zoneId")
	val zoneId: String? = null,

	@field:SerializedName("wardId")
	val wardId: String? = null
){
	override fun toString(): String {
		return wardName ?: ""
	}

}

data class WardListResponse(

	@field:SerializedName("data")
	val data: List<WardItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)
