package com.example.lda.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lda.repository.CaseRepository

class LauncherViewModel(private val repository: CaseRepository) : ViewModel() {

    val isCaseLoading = repository.isCaseLoading
    val allCases = repository.allCases


}
