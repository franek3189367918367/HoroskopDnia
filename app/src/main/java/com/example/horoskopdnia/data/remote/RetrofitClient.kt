package com.example.horoskopdnia.data.remote

import com.example.horoskopdnia.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val apiService: HoroscopeApiService by lazy {
        retrofit.create(HoroscopeApiService::class.java)
    }
}