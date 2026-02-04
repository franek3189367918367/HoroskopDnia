package com.example.horoskopdnia.domain

import com.example.horoskopdnia.data.model.HoroscopeResponse
import com.example.horoskopdnia.data.remote.RetrofitClient


class HoroscopeRepository {

    suspend fun getDailyHoroscope(sign: String): HoroscopeResponse {

        return RetrofitClient.apiService.getDailyHoroscope(sign = sign)
    }
}