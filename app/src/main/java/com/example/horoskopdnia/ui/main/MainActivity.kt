package com.example.horoskopdnia.ui.main

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

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding


    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val repository = HoroscopeRepository()
        val factory = MainViewModelFactory( application = application, repository = repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]


        setupSpinner()


        setupObservers()


        binding.btnRefresh.setOnClickListener {

            viewModel.selectedSign.value?.let { sign ->
                viewModel.fetchHoroscope(sign)
            }
        }
    }


    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            Constants.ZODIAC_SIGNS // Używamy listy z naszego pliku Constants
        )
        binding.spinnerZodiacSign.adapter = adapter

        viewModel.selectedSign.value?.let { savedSign ->
            val position = Constants.ZODIAC_SIGNS.indexOf(savedSign)
            if (position >= 0) {
                binding.spinnerZodiacSign.setSelection(position, false) // false, żeby nie wywołać listenera przy inicjalizacji
            }
        }


        binding.spinnerZodiacSign.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSign = Constants.ZODIAC_SIGNS[position]

                viewModel.fetchHoroscope(selectedSign)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


    }


    private fun setupObservers() {

        viewModel.horoscopeResult.observe(this) { result ->
            when (result) {
                is Result.Success -> {

                    val horoscope = result.data



                    binding.tvHoroscopeTitle.text = "Horoskop na Dziś dla: ${viewModel.selectedSign.value}"


                    binding.tvHoroscopeDate.text = "Data: ${horoscope.date ?: "Brak danych o dacie"}"


                    binding.tvHoroscopeContent.text = horoscope.horoscopeData
                        ?: "Treść horoskopu jest chwilowo niedostępna. Spróbuj odświeżyć."

                }
                is Result.Error -> {

                    binding.tvHoroscopeTitle.text = "Błąd"
                    binding.tvHoroscopeDate.text = ""
                    binding.tvHoroscopeContent.text = "Nie udało się pobrać horoskopu: ${result.exception.message}"
                    Toast.makeText(this, "Błąd pobierania danych", Toast.LENGTH_LONG).show()
                }

            }
        }


        viewModel.selectedSign.observe(this) { sign ->
            binding.tvHoroscopeTitle.text = "Horoskop na Dziś dla: $sign"
        }
    }
}