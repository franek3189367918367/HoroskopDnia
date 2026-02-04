package com.example.horoskopdnia.data.model

import com.google.gson.annotations.SerializedName


data class HoroscopeResponse(
    @SerializedName("date")
    val date: String?,

    @SerializedName("horoscope_data")
    val horoscopeData: String?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("success")
    val success: Boolean?
)