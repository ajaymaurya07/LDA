package com.example.lda.houseTax.data

import com.google.gson.JsonElement
import com.google.gson.Gson

data class ForgotPasswordRequest(
    val username: String
)

data class ForgotPasswordResponse(
    val status: Boolean,
    val message: String? = null,
    val responseCode: Int,
    val data: JsonElement? = null // Change to JsonElement to handle both Object {} and Array []
) {
    fun getSafeData(): ForgotPasswordData? {
        return try {
            if (data != null && data.isJsonObject) {
                Gson().fromJson(data, ForgotPasswordData::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}

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