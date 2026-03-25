package com.example.horoskopdnia.data.model

import com.google.gson.annotations.SerializedName

// Główna klasa odpowiedzi z API
data class HoroscopeResponse(
    @SerializedName("data")
    val horoscopeInfo: HoroscopeData?, // To jest ten "podfolder" w JSON-ie

    @SerializedName("status")
    val status: Int?,

    @SerializedName("success")
    val success: Boolean?
)

// Nowa klasa, która opisuje to, co jest w środku pola "data"
data class HoroscopeData(
    @SerializedName("date")
    val date: String?,

    @SerializedName("horoscope")
    val horoscopeData: String?
)