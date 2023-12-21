package com.dicoding.nav_capstone.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nav_capstone.data.repository.RempahRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val rempahRepository: RempahRepository) : ViewModel() {

    fun getHomeData() = rempahRepository.getHomeData()

}