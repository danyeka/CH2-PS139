package com.dicoding.nav_capstone.ui.welcome

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler().postDelayed({
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }, 3000)
    }
}