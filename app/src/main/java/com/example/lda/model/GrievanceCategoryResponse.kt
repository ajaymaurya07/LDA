package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class GrievanceCategoryResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("responseCode") val responseCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<GrievanceCategory>
)

data class GrievanceCategory(
    @SerializedName("serviceCode") val serviceCode: Int,
    @SerializedName("serviceName") val serviceName: String,
    @SerializedName("subCategories") val subCategories: List<GrievanceSubCategory>
)

data class GrievanceSubCategory(
    @SerializedName("subCatCode") val subCatCode: Int,
    @SerializedName("subName") val subName: String
)
