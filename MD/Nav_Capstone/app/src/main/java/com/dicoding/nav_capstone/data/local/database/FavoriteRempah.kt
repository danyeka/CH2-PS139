package com.dicoding.nav_capstone.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_rempah")
data class FavoriteRempah (
    @PrimaryKey
    @ColumnInfo("id_rempah")
    var idRempah: Int,

    @ColumnInfo("namaRempah")
    var nama: String? = "",

    @ColumnInfo("latinRempah")
    var latin: String? = "",

    @ColumnInfo("image")
    var image : String? = ""
)