package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class SaveGrievanceResponse(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("responseCode") val responseCode: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: GrievanceData? = null
)

data class GrievanceData(
    @SerializedName("grievance_id") val grievanceId: String? = null
)