package com.dicoding.nav_capstone.ui.resep

import androidx.lifecycle.ViewModel
import com.dicoding.nav_capstone.data.repository.RempahRepository

class ResepViewModel(private val rempahRepository: RempahRepository): ViewModel() {

    fun getResep (id:String) = rempahRepository.getResep(id)
}