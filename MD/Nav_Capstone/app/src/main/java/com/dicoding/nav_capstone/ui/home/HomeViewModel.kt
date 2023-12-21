package com.dicoding.nav_capstone.ui.home

import androidx.lifecycle.ViewModel
import com.dicoding.nav_capstone.data.repository.RempahRepository

class HomeViewModel(private val rempahRepository: RempahRepository) : ViewModel() {

    fun getHomeData() = rempahRepository.getHomeData()

}