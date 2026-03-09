package com.example.lda.houseTax.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "property_table")
data class PropertyEntity(
    @PrimaryKey
    val propertyId: String,
    val ownerName: String,
    val ward: String,
    val mohalla: String,
    val phoneNumber: String
)
