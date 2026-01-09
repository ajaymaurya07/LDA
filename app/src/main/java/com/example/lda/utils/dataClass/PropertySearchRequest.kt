package com.example.lda.utils.dataClass

data class PropertySearchRequest(
    val propertyId: String = "",
    val ownerName: String = "",
    val fatherName: String = "",
    val mobileNo: String = "",
    val zoneId: String = "",
    val wardId: String = "",
    val mohallaId: String = "",
    val chukNo: String = "",
    val houseNo: String = "",
    val ulbId: String = "",
    val searchType: String = ""
)

data class PropertyDetailsRequest(
    val propertyId: String =""
)


