package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null,

	@field:SerializedName("status_code")
	val statusCode: String? = null
)

data class ResultItem(

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("zone")
	val zone: String? = null,

	@field:SerializedName("api_key")
	val apiKey: String? = null,

	@field:SerializedName("user_name")
	val userName: String? = null,

	@field:SerializedName("mobile")
	val mobile: String? = null,

	@field:SerializedName("department")
	val department: String? = null
)
