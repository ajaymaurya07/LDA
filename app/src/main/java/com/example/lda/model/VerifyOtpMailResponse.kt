package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class VerifyOtpMailResponse(

	@field:SerializedName("data")
	val data: List<Any?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)
