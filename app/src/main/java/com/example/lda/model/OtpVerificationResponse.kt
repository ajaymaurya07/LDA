package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class OtpVerificationResponse(

//	@field:SerializedName("data")
//	val data: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)
//{"success":true,"responseCode":1,"message":"OTP verified successfully","data":[],"userId":484848}



