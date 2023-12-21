package com.dicoding.nav_capstone.ui.artikel

import androidx.lifecycle.ViewModel
import com.dicoding.nav_capstone.data.repository.RempahRepository

class ArtikelViewModel(private val rempahRepository: RempahRepository): ViewModel() {

    fun getArtikel (id:String) = rempahRepository.getArtikel(id)
}