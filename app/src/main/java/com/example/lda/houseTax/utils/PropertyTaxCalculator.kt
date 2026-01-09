package com.example.lda.houseTax.utils

import com.example.lda.houseTax.data.PropertyTaxCalculation

object PropertyTaxCalculator {

    fun calculate(
        areaOwn:Double,
        areaRent:Double,
        rate:Double,
        age: Int
    ): PropertyTaxCalculation {

        val depApp = getDepreciationAppreciationByAge(age)

        val effectiveOwnArea=areaOwn*0.80
        val effectiveRentArea=areaRent*0.80

        val mrvOwner= effectiveOwnArea*rate
        val mrvRented= effectiveRentArea*rate

        val arvOwner = mrvOwner * 12
        val arvRented = mrvRented * 12

        val depreciation = arvOwner * depApp.depreciation / 100
        val appreciation = arvRented * depApp.appreciation / 100

        val finalArvOwner = arvOwner - depreciation
        val finalArvRented = arvRented + appreciation

        val ownerTax = finalArvOwner * 0.15
        val rentedTax = finalArvRented * 0.15

        return PropertyTaxCalculation(
            mrvOwner,
            mrvRented,
            arvOwner,
            arvRented,
            depreciation,
            appreciation,
            finalArvOwner,
            finalArvRented,
            ownerTax,
            rentedTax,
            ownerTax + rentedTax
        )
    }
}
