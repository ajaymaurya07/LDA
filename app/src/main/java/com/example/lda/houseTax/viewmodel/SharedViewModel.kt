package com.example.lda.houseTax.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val rentArea = MutableLiveData<String>()
    val ownArea = MutableLiveData<String>()
}
