package com.dicoding.nav_capstone.data.di

import android.content.Context
import com.dicoding.nav_capstone.data.local.database.FavRoomDatabase
import com.dicoding.nav_capstone.data.local.preferences.SessionPreferences
import com.dicoding.nav_capstone.data.local.preferences.datastore
import com.dicoding.nav_capstone.data.remote.retrofit.ApiConfig
import com.dicoding.nav_capstone.data.repository.FavoriteRepository
import com.dicoding.nav_capstone.data.repository.RempahRepository
import com.dicoding.nav_capstone.data.utils.AppExecutors


object Injection {
    fun provideRepository(context: Context): RempahRepository {
        val apiService = ApiConfig.getApiService()
        val sessionPreferences = SessionPreferences.getInstance(context.datastore)
        return RempahRepository.getInstance(apiService, sessionPreferences)
    }

    fun provideFavRepository(context: Context): FavoriteRepository {
        val database = FavRoomDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(dao, appExecutors)
    }
}