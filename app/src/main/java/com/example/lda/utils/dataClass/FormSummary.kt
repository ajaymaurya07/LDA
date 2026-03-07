package com.example.lda.utils.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class FormSummary(
    val title: String,
    val details: List<String>,
    val status: String
)



@Parcelize
data class TransactionItem(
    val title: String,
    val amount: String,
    val status: String,
    val billNo: String,
    val propertyId: String,
    val txnId: String,
    val date: String,
    val financialYear: String,
    val bankRefNo:String,
    val paymentMode:String

) : Parcelable
