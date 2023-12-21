package com.dicoding.nav_capstone.ui.welcome

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.nav_capstone.databinding.ActivitySplashScreenBinding
import com.dicoding.nav_capstone.ui.MainActivity
import com.dicoding.nav_capstone.ui.MainViewModel
import com.dicoding.nav_capstone.ui.ViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        var intent: Intent? = null

        viewModel.session.observe(this) {
            intent = if (it.isLogin) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, WelcomeActivity::class.java)
            }
        }

        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 3000)
    }
}