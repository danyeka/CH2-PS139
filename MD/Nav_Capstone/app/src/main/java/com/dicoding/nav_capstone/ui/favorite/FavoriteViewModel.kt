package com.dicoding.nav_capstone.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.nav_capstone.data.local.database.FavoriteRempah
import com.dicoding.nav_capstone.data.repository.FavoriteRepository

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    fun getFavorite() = favoriteRepository.getFavorite().also {
        Log.d("FavoriteViewModel", "Mengambil data favorit: $it")
    }

    fun addToFavorite(favoriteUser: FavoriteRempah) {
        favoriteRepository.addToFavorite(favoriteUser)
        Log.d("FavoriteViewModel", "Menambahkan ke favorit: $favoriteUser")
    }

    fun getFavoriteById(id: String): LiveData<FavoriteRempah> = favoriteRepository.getFavoriteById(id).also {
        Log.d("FavoriteViewModel", "Mengambil favorit berdasarkan ID: $id")
    }

    fun removeFavorite(favoriteRempah: FavoriteRempah) {
        favoriteRepository.removeFavorite(favoriteRempah)
        Log.d("FavoriteViewModel", "Menghapus dari favorit: $favoriteRempah")
    }
}