package com.example.horoskopdnia.ui.settings

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.horoskopdnia.databinding.ActivitySettingsBinding
import com.example.horoskopdnia.utils.Constants
import com.example.horoskopdnia.utils.SharedPreferencesManager

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var prefs: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = SharedPreferencesManager(this)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            Constants.ZODIAC_SIGNS
        )
        binding.spinnerDefaultSign.adapter = adapter

        val savedSign = prefs.getSelectedSign()
        if (savedSign != null) {
            val position = Constants.ZODIAC_SIGNS.indexOf(savedSign)
            if (position >= 0) binding.spinnerDefaultSign.setSelection(position)
        }

        binding.btnSaveSettings.setOnClickListener {
            val selectedSign = binding.spinnerDefaultSign.selectedItem.toString()
            prefs.saveSelectedSign(selectedSign)
            Toast.makeText(this, "Zapisano domyślny znak: $selectedSign", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnBackFromSettings.setOnClickListener {
            finish()
        }
    }
}