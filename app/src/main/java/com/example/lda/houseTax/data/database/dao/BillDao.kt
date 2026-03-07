package com.example.lda.houseTax.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lda.houseTax.data.database.entity.BillEntity

@Dao
interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: BillEntity)

    @Query("SELECT * FROM bills")
    suspend fun getAllBills(): List<BillEntity>

    @Query("SELECT COUNT(*) FROM bills")
    suspend fun getCount(): Int

    @Query("DELETE FROM bills")
    suspend fun clearBills()
}