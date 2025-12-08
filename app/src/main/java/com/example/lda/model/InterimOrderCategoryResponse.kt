package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class InterimOrderCategoryResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("result")
	val result: CategoryCountData? = null,

	@field:SerializedName("status_code")
	val statusCode: String? = null
)

data class CategoryCountData(

	@field:SerializedName("file_affidavit_count")
	val fileAffidavitCount: String? = null,

	@field:SerializedName("file_counter_affidavit_count")
	val fileCounterAffidavitCount: String? = null,

	@field:SerializedName("person_appearance_count")
	val personAppearanceCount: String? = null,

	@field:SerializedName("issue_notice_count")
	val issueNoticeCount: String? = null,

	@field:SerializedName("make_payment_count")
	val makePaymentCount: String? = null,

	@field:SerializedName("other_count")
	val otherCount: String? = null
)
