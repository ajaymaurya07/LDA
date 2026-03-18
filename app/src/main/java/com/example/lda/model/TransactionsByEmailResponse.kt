package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class TransactionsByEmailResponse(
    @SerializedName("status") val status: Boolean? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: List<TransactionData>? = null
)

data class TransactionData(
    @SerializedName("payment_amount") val paymentAmount: String? = null,
    @SerializedName("bill_no") val billNo: String? = null,
    @SerializedName("property_id") val propertyId: String? = null,
    @SerializedName("txnid") val txnId: String? = null,
    @SerializedName("date_time") val dateTime: String? = null,
    @SerializedName("financial_year") val financialYear: String? = null,
    @SerializedName("payment_mode") val paymentMode: String? = null,
    @SerializedName("bank_ref_no") val bankRefNo: String? = null,
    @SerializedName("transaction_status") val transactionStatus: String? = null
)
