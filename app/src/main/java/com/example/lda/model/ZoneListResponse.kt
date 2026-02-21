package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class ZoneListResponse(

	@field:SerializedName("data")
	val data: List<ZoneItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)

data class ZoneItem(

	@field:SerializedName("ulbId")
	val ulbId: String? = null,

	@field:SerializedName("zoneId")
	val zoneId: String? = null,

	@field:SerializedName("zoneName")
	val zoneName: String? = null
){
	override fun toString(): String {
		return zoneName ?: ""
	}

}
