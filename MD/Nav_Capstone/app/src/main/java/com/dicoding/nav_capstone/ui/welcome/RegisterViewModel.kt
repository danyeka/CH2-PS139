package com.dicoding.nav_capstone.ui.welcome

import androidx.lifecycle.ViewModel
import com.dicoding.nav_capstone.data.repository.RempahRepository

class RegisterViewModel(private val rempahRepository: RempahRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) =
        rempahRepository.register(name, email, password)
}

