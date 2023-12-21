package com.dicoding.nav_capstone.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nav_capstone.data.local.model.SessionModel
import com.dicoding.nav_capstone.data.repository.RempahRepository
import kotlinx.coroutines.launch

class ProfileViewModel (private val rempahRepository: RempahRepository) : ViewModel() {
    fun logOut() = viewModelScope.launch {
        try {
            // Ensure that the repository function is invoked properly
            rempahRepository.logOut()
        } catch (e: Exception) {
            // Handle any exceptions that may occur during logout
            Log.e("ProfileViewModel", "Error during logout", e)
        }
    }

//    suspend fun getUsername (name: String, email: String) = rempahRepository.saveSession(SessionModel(name,true,email))

}