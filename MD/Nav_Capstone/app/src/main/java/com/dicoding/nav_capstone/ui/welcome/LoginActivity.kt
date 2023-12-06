package com.dicoding.nav_capstone.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.databinding.ActivityLoginBinding
import com.dicoding.nav_capstone.ui.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        supportActionBar?.hide()
    }
}