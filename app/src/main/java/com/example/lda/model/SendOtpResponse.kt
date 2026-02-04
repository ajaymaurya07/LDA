package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class SendOtpResponse(

	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("userId")
	val userId: Any? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)
