package com.example.lda.houseTax.data

data class RateMaster(
    val WardNo: String,
    val WardName: String,

    val pakkaRccGt24: Double,
    val pakkaRcc12to24: Double,
    val pakkaRccLt12: Double,

    val otherPakkaGt24: Double,
    val otherPakka12to24: Double,
    val otherPakkaLt12: Double,

    val kachaGt24: Double,
    val kacha12to24: Double,
    val kachaLt12: Double,

    val plotGt24: Double,
    val plot12to24: Double,
    val plotLt12: Double
)
