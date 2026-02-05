package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class SignUpResponse(


	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)

//data class Sign(
//
//	@field:SerializedName("email")
//	val email: String? = null
//)
