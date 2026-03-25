package com.example.horoskopdnia.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.horoskopdnia.databinding.ActivityMenuBinding
import com.example.horoskopdnia.ui.details.DetailActivity
import com.example.horoskopdnia.ui.settings.SettingsActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. NAJPIERW inicjujemy binding
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. TERAZ możemy bezpiecznie ustawiać listenery
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }

    }
}