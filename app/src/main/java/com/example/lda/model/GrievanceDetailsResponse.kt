package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class GrievanceDetailsResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: List<GrievanceDetails>?
)

data class GrievanceDetails(
    @SerializedName("grievance_no") val grievanceNo: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("father_name") val fatherName: String?,
    @SerializedName("mobile_no") val mobileNo: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("category_name") val categoryName: String?,
    @SerializedName("subcategory_name") val subcategoryName: String?
)
