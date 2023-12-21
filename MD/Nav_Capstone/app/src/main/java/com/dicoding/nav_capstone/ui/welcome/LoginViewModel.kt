package com.dicoding.nav_capstone.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nav_capstone.data.local.model.SessionModel
import com.dicoding.nav_capstone.data.repository.RempahRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val rempahRepository: RempahRepository) : ViewModel() {
    fun login(email: String, password: String) = rempahRepository.login(email, password)
    fun saveSession(sessionModel: SessionModel) = viewModelScope.launch {
        rempahRepository.saveSession(sessionModel)
    }

}