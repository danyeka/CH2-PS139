package com.dicoding.nav_capstone.ui.list

import androidx.lifecycle.ViewModel
import com.dicoding.nav_capstone.data.repository.RempahRepository

class ListViewModel(private val rempahRepository: RempahRepository) : ViewModel() {

    fun getAllRempah() = rempahRepository.getAllRempah(token = String())

}