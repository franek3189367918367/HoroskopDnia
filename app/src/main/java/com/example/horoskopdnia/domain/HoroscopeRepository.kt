package com.example.horoskopdnia.domain

import com.example.horoskopdnia.data.model.HoroscopeResponse
import com.example.horoskopdnia.data.remote.RetrofitClient
import com.example.horoskopdnia.utils.Result
import android.util.Log
class HoroscopeRepository {

    suspend fun getDailyHoroscope(sign: String): Result<HoroscopeResponse> {
        return try {

            val response = RetrofitClient.apiService.getDailyHoroscope(sign)
            Log.d("API_DEBUG", "Odpowiedź z serwera: $response")
            Result.Success(response)

        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}