package com.dicoding.nav_capstone.ui.welcome

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.nav_capstone.data.local.model.SessionModel
import com.dicoding.nav_capstone.data.repository.ResultState
import com.dicoding.nav_capstone.databinding.ActivityLoginBinding
import com.dicoding.nav_capstone.ui.MainActivity
import com.dicoding.nav_capstone.ui.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val email = binding.regEditEmail.text
            val password = binding.regEditPassword.text
            viewModel.login(email.toString(), password.toString())
                .observe(this) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ResultState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val loginResult = result.data.loginResult

                            if (loginResult != null) {
                                val sessionModel = SessionModel(loginResult.token, true, loginResult.email)
                                viewModel.saveSession(sessionModel)

                                saveSessionToSharedPreferences(sessionModel)

                                AlertDialog.Builder(this@LoginActivity).apply {
//                                    setTitle("Login Berhasil!")
                                    setMessage("Login berhasil!")
                                    setPositiveButton("Masuk") { _, _ ->
                                        val intent = Intent(context, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            } else {
                                // Handle case when loginResult is null
                                Toast.makeText(application, "Masukkan kembali email/password yang benar", Toast.LENGTH_LONG).show()
                            }
                        }


                        is ResultState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                application,
                                "Error: ${result.error}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        else -> {}
                    }
                }
        }

        binding.tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.backButton.setOnClickListener {
            // Navigate back when the backButton is clicked
            onBackPressed()
        }

        supportActionBar?.hide()

    }

    private fun saveSessionToSharedPreferences(sessionModel: SessionModel){
        val sharedPref = getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("token", sessionModel.token)
        editor.putBoolean("isLogin", sessionModel.isLogin)
        editor.apply()
    }
}