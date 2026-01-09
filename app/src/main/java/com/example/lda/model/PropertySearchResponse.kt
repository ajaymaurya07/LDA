package com.example.lda.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PropertySearchResponse(

	@field:SerializedName("data")
	val data: List<PropertyItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)

@Parcelize
data class PropertyItem(

	@field:SerializedName("oldPropertyId")
	val oldPropertyId: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("ownerName")
	val ownerName: String? = null,

	@field:SerializedName("totalArv")
	val totalArv: Double? = null,

	@field:SerializedName("propertyType")
	val propertyType: String? = null,

	@field:SerializedName("fatherHusbandName")
	val fatherHusbandName: String? = null,

	@field:SerializedName("finYear")
	val finYear: String? = null,

	@field:SerializedName("houseNo")
	val houseNo: String? = null,

	@field:SerializedName("chukNo")
	val chukNo: String? = null,

	@field:SerializedName("propertyId")
	val propertyId: String? = null,

	@field:SerializedName("billNo")
	val billNo: String? = null,

	@field:SerializedName("totalArea")
	val totalArea: String? = null
): Parcelable
