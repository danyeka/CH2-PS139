package com.dicoding.nav_capstone.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nav_capstone.data.repository.RempahRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val rempahRepository: RempahRepository) : ViewModel() {
    fun logOut() = viewModelScope.launch {
        try {
            rempahRepository.logOut()
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "Error during logout", e)
        }
    }
}