package com.dicoding.nav_capstone.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.databinding.ActivityLoginBinding
import com.dicoding.nav_capstone.databinding.ActivitySubscriptionBinding
import com.dicoding.nav_capstone.ui.ViewModelFactory
import com.dicoding.nav_capstone.ui.welcome.LoginViewModel

class SubscriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            // Navigate back when the backButton is clicked
            onBackPressed()
        }

        binding.langgananButton.setOnClickListener{
            Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }
}