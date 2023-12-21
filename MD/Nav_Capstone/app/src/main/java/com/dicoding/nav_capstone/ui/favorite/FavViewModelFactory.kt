package com.dicoding.nav_capstone.ui.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.nav_capstone.data.di.Injection
import com.dicoding.nav_capstone.data.repository.FavoriteRepository

class FavViewModelFactory private constructor(private val favoriteRepository: FavoriteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavViewModelFactory? = null
        fun getInstance(context: Context): FavViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavViewModelFactory(Injection.provideFavRepository(context))
            }.also { instance = it }
    }
}