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