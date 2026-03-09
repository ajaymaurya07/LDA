package com.example.lda.houseTax.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lda.houseTax.data.database.dao.BillDao
import com.example.lda.houseTax.data.database.dao.PropertyDao
import com.example.lda.houseTax.data.database.entity.BillEntity
import com.example.lda.houseTax.data.database.entity.PropertyEntity

@Database(entities = [BillEntity::class, PropertyEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun billDao(): BillDao
    abstract fun propertyDao(): PropertyDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bill_database"
                )
                .fallbackToDestructiveMigration() // Version change handle karne ke liye
                .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
