package com.example.lda.houseTax.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lda.houseTax.data.database.AppDatabase
import com.example.lda.houseTax.data.database.entity.PropertyEntity
import kotlinx.coroutines.launch

class PropertyIdFromDb(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val propertyDao = db.propertyDao()

    val propertyList = MutableLiveData<List<PropertyEntity>>()

    fun loadProperties() {
        viewModelScope.launch {
            Log.d("TAG", "loadProperties: ")
            propertyList.value = propertyDao.getAllProperties()
        }
    }
}