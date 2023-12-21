package com.dicoding.nav_capstone.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(favoriteRempah: FavoriteRempah)

    @Query("SELECT * FROM favorite_rempah")
    fun getFavorite(): LiveData<List<FavoriteRempah>>

    @Query("SELECT * FROM favorite_rempah WHERE favorite_rempah.id_rempah = :idRempah")
    fun getFavoriteById(idRempah: String): LiveData<FavoriteRempah>

    @Delete
    fun removeFavorite(favoriteUser: FavoriteRempah)

}