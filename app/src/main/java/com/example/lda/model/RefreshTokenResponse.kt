package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("responseCode")
    val responseCode: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: RefreshTokenData? = null
)

data class RefreshTokenData(
    @field:SerializedName("access_token")
    val accessToken: String? = null
)
