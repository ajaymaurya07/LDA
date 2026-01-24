package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class CreateTransactionResponse(

	@field:SerializedName("data")
	val data: Transaction? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Transaction(

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("firstname")
	val firstname: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("furl")
	val furl: String? = null,

	@field:SerializedName("surl")
	val surl: String? = null,

	@field:SerializedName("productinfo")
	val productinfo: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("key")
	val key: String? = null,

	@field:SerializedName("txnid")
	val txnid: String? = null
)
