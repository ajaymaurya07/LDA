package com.example.lda.houseTax.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: com.example.lda.houseTax.data.database.entity.PropertyEntity)

    @Query("SELECT * FROM property_table")
    suspend fun getAllProperties(): List<com.example.lda.houseTax.data.database.entity.PropertyEntity>

    @Query("DELETE FROM property_table WHERE propertyId = :id")
    suspend fun deletePropertyById(id: String)
}
