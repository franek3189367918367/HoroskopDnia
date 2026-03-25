package com.example.horoskopdnia.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.horoskopdnia.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicjalizacja ViewBinding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Odbieramy tekst przesłany z MainActivity
        val horoscope = intent.getStringExtra("KLUCZ_HOROSKOPU")

        // Wyświetlamy treść w TextView (id: tvDetails)
        binding.tvDetails.text = horoscope ?: "Nie otrzymano treści horoskopu."

        // Obsługa przycisku powrotu
        binding.btnBack.setOnClickListener {
            finish() // Zamyka ten ekran i wraca do poprzedniego (MainActivity)
        }
    }
}