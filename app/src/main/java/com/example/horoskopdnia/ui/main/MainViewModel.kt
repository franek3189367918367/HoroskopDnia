package com.example.horoskopdnia.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horoskopdnia.data.model.HoroscopeResponse
import com.example.horoskopdnia.domain.HoroscopeRepository
import com.example.horoskopdnia.utils.Constants
import kotlinx.coroutines.launch
import com.example.horoskopdnia.utils.SharedPreferencesManager
import com.example.horoskopdnia.utils.Result

class MainViewModel(
    private val repository: HoroscopeRepository,
    private val prefsManager: SharedPreferencesManager
) : ViewModel() {

    private val _horoscopeResult = MutableLiveData<Result<HoroscopeResponse>>()
    val horoscopeResult: LiveData<Result<HoroscopeResponse>> = _horoscopeResult

    private val _selectedSign = MutableLiveData<String?>()
    val selectedSign: LiveData<String?> = _selectedSign

    init {
        val savedSign: String? = prefsManager.getSelectedSign()
        if (savedSign != null) {
            _selectedSign.value = savedSign
            fetchHoroscope(savedSign)
        } else if (Constants.ZODIAC_SIGNS.isNotEmpty()) {
            val defaultSign = Constants.ZODIAC_SIGNS[0]
            _selectedSign.value = defaultSign
            fetchHoroscope(defaultSign)
        }
    }

    fun fetchHoroscope(sign: String) {
        _selectedSign.value = sign

        viewModelScope.launch {
            // --- TUTAJ DODAJEMY STAN ŁADOWANIA ---
            _horoscopeResult.value = Result.Loading

            // Pobieramy dane z repozytorium
            val response = repository.getDailyHoroscope(sign.lowercase())

            // Przypisujemy wynik (Success lub Error zwrócony przez repo)
            _horoscopeResult.value = response
        }

        prefsManager.saveSelectedSign(sign)
    }
}
