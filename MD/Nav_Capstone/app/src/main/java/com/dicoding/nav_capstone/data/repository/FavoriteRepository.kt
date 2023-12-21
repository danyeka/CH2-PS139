package com.dicoding.nav_capstone.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.dicoding.nav_capstone.data.local.database.FavoriteDao
import com.dicoding.nav_capstone.data.local.database.FavoriteRempah
import com.dicoding.nav_capstone.data.utils.AppExecutors

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {

    fun addToFavorite(favoriteRempah: FavoriteRempah) {
        appExecutors.diskIO.execute {
            Log.d("FavoriteRepository", "Adding to favorite: $favoriteRempah")
            favoriteDao.addToFavorite(favoriteRempah)
        }
    }

    fun getFavorite(): LiveData<List<FavoriteRempah>> {
        return favoriteDao.getFavorite()
    }

    fun getFavoriteById(id: String): LiveData<FavoriteRempah> {
        return favoriteDao.getFavoriteById(id)
    }

    fun removeFavorite(favoriteRempah: FavoriteRempah) {
        appExecutors.diskIO.execute {
            Log.d("FavoriteRepository", "Removing from favorite: $favoriteRempah")
            favoriteDao.removeFavorite(favoriteRempah)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteDao, appExecutors)
            }.also { instance = it }
    }

}