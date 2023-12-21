package com.dicoding.nav_capstone.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.nav_capstone.data.repository.ResultState
import com.dicoding.nav_capstone.databinding.ActivityRegisterBinding
import com.dicoding.nav_capstone.ui.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegDaftar.setOnClickListener {
            val name = binding.regEditName.text
            val email = binding.regEditEmail.text
            val password = binding.regEditPassword.text

            viewModel.register(name.toString(), email.toString(), password.toString())
                .observe(this) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ResultState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            AlertDialog.Builder(this@RegisterActivity).apply {
                                setMessage("Register berhasil!")
                                setPositiveButton("Login") { _, _ ->
                                    val intent = Intent(context, LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }

                        is ResultState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                applicationContext,
                                "Error: ${result.error}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
        }
        binding.tvMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}
