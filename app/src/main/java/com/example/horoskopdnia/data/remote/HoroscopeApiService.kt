package com.example.horoskopdnia.data.remote

import com.example.horoskopdnia.data.model.HoroscopeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HoroscopeApiService {


    @GET("api/v1/get-horoscope/daily")
    suspend fun getDailyHoroscope(

        @Query("sign") sign: String,


        @Query("day") day: String = "TODAY"
    ): HoroscopeResponse
}