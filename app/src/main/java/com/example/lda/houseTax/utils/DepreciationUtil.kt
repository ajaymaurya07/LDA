package com.example.lda.houseTax.utils


data class DepAppResult(
    val depreciation: Double,
    val appreciation: Double
)

fun getDepreciationAppreciationByAge(age: Int): DepAppResult {
    return when {
        age < 10 -> DepAppResult(25.0, 25.0)
        age in 10..20 -> DepAppResult(32.5, 12.5)
        else -> DepAppResult(40.0, 0.0)
    }
}
