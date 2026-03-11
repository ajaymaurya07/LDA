package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class FetchGrievanceResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)

data class SubCategoriesItem(

	@field:SerializedName("subCatCode")
	val subCatCode: Int? = null,

	@field:SerializedName("subName")
	val subName: String? = null
)

data class DataItem(

	@field:SerializedName("serviceCode")
	val serviceCode: Int? = null,

	@field:SerializedName("serviceName")
	val serviceName: String? = null,

	@field:SerializedName("subCategories")
	val subCategories: List<SubCategoriesItem?>? = null
)
