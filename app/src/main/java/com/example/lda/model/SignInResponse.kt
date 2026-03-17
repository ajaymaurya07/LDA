package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class SignInResponse(

	@field:SerializedName("data")
	val data: SignIn? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)

data class SignIn(

	@field:SerializedName("access_token")
	val accessToken: Any? = null,

	@field:SerializedName("refresh_token")
	val refreshToken: Any? = null,


	@field:SerializedName("email_id")
	val emailId: Any? = null,


	@field:SerializedName("user_type")
	val userType: Any? = null,


)
