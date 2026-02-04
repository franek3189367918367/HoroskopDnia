package com.example.horoskopdnia.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.horoskopdnia.domain.HoroscopeRepository
import com.example.horoskopdnia.utils.SharedPreferencesManager

class MainViewModelFactory(
    private val application: Application,
    private val repository: HoroscopeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            // Przekazujemy menedżera preferencji do ViewModel
            val prefsManager = SharedPreferencesManager(application) // UTWORZENIE MENEDŻERA
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository, prefsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}