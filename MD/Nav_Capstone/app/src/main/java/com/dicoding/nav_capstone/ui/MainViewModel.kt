package com.dicoding.nav_capstone.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.nav_capstone.data.repository.RempahRepository


class MainViewModel(private val rempahRepository: RempahRepository) : ViewModel() {
    val session = rempahRepository.getSession().asLiveData()

}