package com.example.lda.eCourtUi


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lda.repository.CaseRepository
import com.example.lda.viewmodel.LauncherViewModel

class LauncherViewModelFactory(private val repo: CaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LauncherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LauncherViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
