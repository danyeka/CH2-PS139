package com.dicoding.nav_capstone.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.nav_capstone.databinding.ActivitySubscriptionBinding

class SubscriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.langgananButton.setOnClickListener {
            Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }
}