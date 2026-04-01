package com.example.lda.houseTax.data

data class ForgotPasswordRequest(
    val email_or_mobile: String
)

data class ForgotPasswordResponse(
    val status: Boolean,
    val message: String? = null,
    val responseCode: Int,
    val data: ForgotPasswordData? = null
)

data class ForgotPasswordData(
    val email_hint: String? = null
)

data class VerifyForgotPasswordOtpRequest(
    val username: String,
    val otp: String,
    val new_password: String,
    val confirm_password: String
)

data class VerifyForgotPasswordOtpResponse(
    val status: Boolean,
    val responseCode: Int,
    val message: String,
    val data: List<Any>? = null
)