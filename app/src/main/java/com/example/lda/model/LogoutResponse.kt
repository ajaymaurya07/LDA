package com.example.lda.model

data class LogoutResponse(
    val status: Boolean,
    val message: String,
    val responseCode: Int
)