package com.dicoding.nav_capstone.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.nav_capstone.data.di.Injection
import com.dicoding.nav_capstone.data.repository.RempahRepository
import com.dicoding.nav_capstone.ui.artikel.ArtikelViewModel
import com.dicoding.nav_capstone.ui.detail.DetailViewModel
import com.dicoding.nav_capstone.ui.home.HomeViewModel
import com.dicoding.nav_capstone.ui.list.ListViewModel
import com.dicoding.nav_capstone.ui.profile.ProfileViewModel
import com.dicoding.nav_capstone.ui.resep.ResepViewModel
import com.dicoding.nav_capstone.ui.scan.ScanViewModel
import com.dicoding.nav_capstone.ui.welcome.LoginViewModel
import com.dicoding.nav_capstone.ui.welcome.RegisterViewModel

class ViewModelFactory private constructor(private val rempahRepository: RempahRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(rempahRepository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(rempahRepository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(rempahRepository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(rempahRepository) as T
        } else if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(rempahRepository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(rempahRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(rempahRepository) as T
        } else if (modelClass.isAssignableFrom(ResepViewModel::class.java)) {
            return ResepViewModel(rempahRepository) as T
        } else if (modelClass.isAssignableFrom(ArtikelViewModel::class.java)) {
            return ArtikelViewModel(rempahRepository) as T
        } else if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            return ScanViewModel(rempahRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}