package com.example.horoskopdnia.utils

import android.content.Context


class SharedPreferencesManager(context: Context) {


    private val PREF_SIGN = "selected_zodiac_sign"


    private val sharedPreferences =
        context.getSharedPreferences("horoscope_app_prefs", Context.MODE_PRIVATE)


    fun saveSelectedSign(sign: String) {
        sharedPreferences.edit()
            .putString(PREF_SIGN, sign)
            .apply()
    }


    fun getSelectedSign(): String? {

        return sharedPreferences.getString(PREF_SIGN, null)
    }
}