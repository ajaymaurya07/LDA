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

    val totalArv: String,
    val user_id: String,

    val email_id: String
)


data class SendOtpRequest(
    val mobileNo: String,
    val propertyId:String
)


data class VerifyOtpRequest(
    val mobileNo: String,
    val otp: String
)


data class VerifyOtpRequestForGrievance(
    val mobileNo: String,
    val otp: String,
    val grievance_id: String
)

data class SignUpRequest(
    val name: String,
    val mobile_no: String,
    val email: String,
    val password: String
)


data class VerifyOtpMailRequest(
    val email: String,
    val otp: String
)



data class SignInRequest(
    val username: String,
    val device_id: String,
    val challenge_id: String,
    val timestamp: String,
    val nonce: String,
    val hash: String
)

data class PaymentAlert(
    val title: String,
    val message: String,
    val color: Int
)

