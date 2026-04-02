package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class GrievanceStatusResponse(
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("responseCode") val responseCode: Int = 0,
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: List<GrievanceStatusData>? = emptyList()
)

data class GrievanceStatusData(
    @SerializedName("ulbName") val ulbName: String? = null,
    @SerializedName("mohallaName") val mohallaName: String? = null,
    @SerializedName("zoneName") val zoneName: String? = null,
    @SerializedName("wardName") val wardName: String? = null,
    @SerializedName("complaintId") val complaintId: String? = null,
    @SerializedName("complaintDate") val complaintDate: String? = null,
    @SerializedName("categoryName") val categoryName: String? = null,
    @SerializedName("subCategoryName") val subCategoryName: String? = null,
    @SerializedName("landmark") val landmark: String? = null,
    @SerializedName("complaintDesc") val complaintDesc: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("fatherHusbandName") val fatherHusbandName: String? = null,
    @SerializedName("mobile") val mobile: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("address1") val address1: String? = null,
    @SerializedName("address2") val address2: String? = null,
    @SerializedName("assignedEmpName") val assignedEmpName: String? = null,
    @SerializedName("assignedEmpMobile") val assignedEmpMobile: String? = null,
    @SerializedName("assignedEmpPost") val assignedEmpPost: String? = null,
    @SerializedName("assignedOffName") val assignedOffName: String? = null,
    @SerializedName("assignedOffMobile") val assignedOffMobile: String? = null,
    @SerializedName("assignedOffPost") val assignedOffPost: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("closeDate") val closeDate: String? = null,
    @SerializedName("closeRemark") val closeRemark: String? = null,
    @SerializedName("complaintTime") val complaintTime: String? = null,
    @SerializedName("closeTime") val closeTime: String? = null,
    @SerializedName("reComplain") val reComplain: Int? = null,
    @SerializedName("dueDate") val dueDate: String? = null
)