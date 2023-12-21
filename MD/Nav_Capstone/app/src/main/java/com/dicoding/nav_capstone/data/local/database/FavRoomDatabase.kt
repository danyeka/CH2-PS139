package com.dicoding.nav_capstone.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [FavoriteRempah::class],
    version = 5
)
abstract class FavRoomDatabase : RoomDatabase() {
    companion object {
        @Volatile
        var INSTANCE: FavRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavRoomDatabase::class.java,
                        "fav_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                }
            }
            return INSTANCE as FavRoomDatabase
        }

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }
    }

    abstract fun favoriteDao(): FavoriteDao
}