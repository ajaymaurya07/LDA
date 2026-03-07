package com.example.lda.houseTax.data.repository

import com.example.lda.houseTax.data.database.dao.BillDao
import com.example.lda.houseTax.data.database.entity.BillEntity

class DataRepository(private val dao: BillDao) {

    suspend fun insertBill(bill: BillEntity) {
        dao.insertBill(bill)
    }
    suspend fun getAllBills(): List<BillEntity> {
        return dao.getAllBills()
    }
}