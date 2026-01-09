package com.example.lda.houseTax.data


data class PropertyTaxCalculation(
    val mrvOwner: Double,
    val mrvRented: Double,

    val arvOwner: Double,
    val arvRented: Double,

    val depreciation: Double,
    val appreciation: Double,

    val finalArvOwner: Double,
    val finalArvRented: Double,

    val ownerTax: Double,
    val rentedTax: Double,

    val totalTax: Double
)


data class PropertyDetails(
    val PropertyId: String,
    val OwnerName: String,
    val MobileNo: String,
)

data class AreaAndStructureDetails(
    val areaRate:String,
    val constructionYear:String,
    val ageOfStructure:String
)


