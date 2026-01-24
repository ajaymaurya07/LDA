package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class TransactionsDetailsResponse(

	@field:SerializedName("data")
	val data: TransactionsDetails? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TransactionsDetails(

	@field:SerializedName("net_demand")
	val netDemand: String? = null,

	@field:SerializedName("water_tax")
	val waterTax: String? = null,

	@field:SerializedName("payu_status")
	val payuStatus: String? = null,

	@field:SerializedName("financial_year")
	val financialYear: String? = null,

	@field:SerializedName("net_payable")
	val netPayable: String? = null,

	@field:SerializedName("mobile_no")
	val mobileNo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("bill_no")
	val billNo: String? = null,

	@field:SerializedName("bank_ref_no")
	val bankRefNo: String? = null,

	@field:SerializedName("ulb_push_at")
	val ulbPushAt: String? = null,

	@field:SerializedName("payu_error")
	val payuError: Any? = null,

	@field:SerializedName("water_charge")
	val waterCharge: String? = null,

	@field:SerializedName("payu_payload")
	val payuPayload: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("ulb_push_request")
	val ulbPushRequest: String? = null,

	@field:SerializedName("sewer_tax")
	val sewerTax: String? = null,

	@field:SerializedName("ulb_push_response")
	val ulbPushResponse: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("txnid")
	val txnid: String? = null,

	@field:SerializedName("payment_mode")
	val paymentMode: String? = null,

	@field:SerializedName("mobile_transaction_id")
	val mobileTransactionId: String? = null,

	@field:SerializedName("owner_name")
	val ownerName: String? = null,

	@field:SerializedName("payment_status")
	val paymentStatus: String? = null,

	@field:SerializedName("ulb_id")
	val ulbId: String? = null,

	@field:SerializedName("property_id")
	val propertyId: String? = null,

	@field:SerializedName("father_name")
	val fatherName: String? = null,

	@field:SerializedName("other_tax")
	val otherTax: String? = null,

	@field:SerializedName("mobile_transaction_timestamp")
	val mobileTransactionTimestamp: String? = null,

	@field:SerializedName("property_tax")
	val propertyTax: String? = null,

	@field:SerializedName("ulb_receipt_pushed")
	val ulbReceiptPushed: String? = null
)