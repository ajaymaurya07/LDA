package com.example.lda.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SubOrdinateCaseViewModel:ViewModel() {
    val isOpened = MutableLiveData<Boolean>()

    fun toggleData(data:Boolean){
        isOpened.value = data
    }
}