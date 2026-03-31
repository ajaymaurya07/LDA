package com.example.lda.houseTax.data

data class ChallengeRequest(
    val username: String,
    val device_id: String
)

data class ChallengeResponse(
    val responseCode: Int,
    val data: ChallengeData?,
    val message: String? = null,
    val status: Boolean? = null
)

data class ChallengeData(
    val challenge_id: String,
    val challenge: String,
    val timestamp: String
)