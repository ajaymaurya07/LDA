package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class SaveGrievanceResponse(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("grievance_id") val grievanceId: Int? = null
)
