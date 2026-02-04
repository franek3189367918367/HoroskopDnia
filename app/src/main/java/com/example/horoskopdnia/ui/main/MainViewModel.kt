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
            try {


                val response = repository.getDailyHoroscope(sign)

                _horoscopeResult.value = Result.Success(response)

            } catch (e: Exception) {
                _horoscopeResult.value = Result.Error(e)
            }
        }
        prefsManager.saveSelectedSign(sign)
    }
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}