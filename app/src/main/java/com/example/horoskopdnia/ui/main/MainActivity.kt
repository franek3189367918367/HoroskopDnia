package com.example.horoskopdnia.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.horoskopdnia.databinding.ActivityMainBinding
import com.example.horoskopdnia.domain.HoroscopeRepository
import com.example.horoskopdnia.utils.Constants
import com.example.horoskopdnia.utils.Result
import com.example.horoskopdnia.ui.menu.MenuActivity
import com.example.horoskopdnia.data.model.HoroscopeResponse
import com.example.horoskopdnia.ui.details.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = HoroscopeRepository()
        val factory = MainViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setupSpinner()
        setupObservers()

        binding.btnRefresh.setOnClickListener {
            viewModel.selectedSign.value?.let { sign ->
                viewModel.fetchHoroscope(sign)
            }
        }

        binding.btnMenu.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        binding.btnDetails.setOnClickListener {
            val currentResult = viewModel.horoscopeResult.value

            if (currentResult is Result.Success) {
                val tekst = currentResult.data.horoscopeInfo?.horoscopeData

                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("KLUCZ_HOROSKOPU", tekst)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Najpierw pobierz horoskop!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            Constants.ZODIAC_SIGNS
        )
        binding.spinnerZodiacSign.adapter = adapter

        viewModel.selectedSign.value?.let { savedSign ->
            val position = Constants.ZODIAC_SIGNS.indexOf(savedSign)
            if (position >= 0) {
                binding.spinnerZodiacSign.setSelection(position, false)
            }
        }

        binding.spinnerZodiacSign.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedSign = Constants.ZODIAC_SIGNS[position]
                    viewModel.fetchHoroscope(selectedSign)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setupObservers() {
        viewModel.horoscopeResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val response = result.data
                    val dataInside = response.horoscopeInfo
                    val signName = viewModel.selectedSign.value ?: ""
                    val emoji = getZodiacEmoji(signName)

                    binding.tvHoroscopeTitle.text = "$emoji Horoskop dla: $signName"
                    binding.tvHoroscopeDate.text = "Data: ${dataInside?.date ?: "Brak daty"}"
                    binding.tvHoroscopeContent.text = dataInside?.horoscopeData ?: "Brak treści"
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvHoroscopeTitle.text = "Błąd"
                    binding.tvHoroscopeDate.text = ""
                    binding.tvHoroscopeContent.text = "Nie udało się pobrać horoskopu"
                    Toast.makeText(this, "Błąd połączenia", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

        viewModel.selectedSign.observe(this) { sign ->
            val emoji = getZodiacEmoji(sign ?: "")
            binding.tvHoroscopeTitle.text = "$emoji Horoskop dla: $sign"

            // AKTUALIZACJA TŁA
            updateBackground(sign ?: "")
        }
    }

    private fun updateBackground(sign: String) {
        val resourceName = "bg_${sign.lowercase()}"
        val resId = resources.getIdentifier(resourceName, "drawable", packageName)

        if (resId != 0) {
            binding.root.setBackgroundResource(resId)
        }
    }

    private fun getZodiacEmoji(sign: String): String {
        return when (sign.lowercase()) {
            "aries" -> "♈"
            "taurus" -> "♉"
            "gemini" -> "♊"
            "cancer" -> "♋"
            "leo" -> "♌"
            "virgo" -> "♍"
            "libra" -> "♎"
            "scorpio" -> "♏"
            "sagittarius" -> "♐"
            "capricorn" -> "♑"
            "aquarius" -> "♒"
            "pisces" -> "♓"
            else -> "✨"
        }
    }
}