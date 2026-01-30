package com.example.lda.houseTax.data

data class InitiateTransactionRequest(

    val mobile_transaction_id: String,
    val mobile_transaction_timestamp: String,
    val bill_no: String,

    val property_id: String,
    val ulb_id: String,
    val financial_year: String,

    val ownerName: String,
    val fatherName: String,
    val mobileNo: String,

    val property_tax: String,
    val water_tax: String,
    val sewer_tax: String,
    val other_tax: String,
    val water_charge: String,

    val net_demand: String,
    val net_payable: String,

    val totalArv: String
)
