package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class TransactionsDetailsResponse(

	@field:SerializedName("data")
	val data: TransactionsDetails? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class TransactionsDetails(

	@field:SerializedName("payment_mode")
	val paymentMode: Any? = null,

	@field:SerializedName("owner_name")
	val ownerName: String? = null,

	@field:SerializedName("sewerTaxPaid")
	val sewerTaxPaid: String? = null,

	@field:SerializedName("payment_status")
	val paymentStatus: String? = null,

	@field:SerializedName("net_payable")
	val netPayable: String? = null,

	@field:SerializedName("mobile_no")
	val mobileNo: String? = null,

	@field:SerializedName("financialYear")
	val financialYear: String? = null,

	@field:SerializedName("otherTaxPaid")
	val otherTaxPaid: String? = null,

	@field:SerializedName("mobile_transaction_timestamp")
	val mobileTransactionTimestamp: String? = null,

	@field:SerializedName("waterTaxPaid")
	val waterTaxPaid: String? = null,

	@field:SerializedName("billNo")
	val billNo: String? = null,

	@field:SerializedName("propertyId")
	val propertyId: String? = null,

	@field:SerializedName("waterChargePaid")
	val waterChargePaid: String? = null,

	@field:SerializedName("propertyTaxPaid")
	val propertyTaxPaid: String? = null,

	@field:SerializedName("txnid")
	val txnid: String? = null
)
