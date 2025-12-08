package com.example.lda.eCourtUi.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CaseDao {

    @Query("SELECT * FROM cases")
    suspend fun getAllCases(): List<CaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cases: List<CaseEntity>)

    @Query("DELETE FROM cases")
    suspend fun clearAll()
}
