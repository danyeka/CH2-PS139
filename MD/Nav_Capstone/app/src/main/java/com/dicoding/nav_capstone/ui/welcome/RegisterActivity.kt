package com.dicoding.nav_capstone.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.databinding.ActivityLoginBinding
import com.dicoding.nav_capstone.databinding.ActivityRegisterBinding
import com.dicoding.nav_capstone.ui.MainActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegDaftar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.tvMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        supportActionBar?.hide()
    }
}