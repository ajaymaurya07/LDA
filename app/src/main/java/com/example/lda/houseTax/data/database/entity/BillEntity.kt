package com.example.lda.houseTax.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bills")
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val financialYear: String,
    val billDate: String,
    val paymentDate: String
)