package com.leftmyhand.githubusers.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUsers::class], version = 1)
abstract class FavDB : RoomDatabase() {
    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE: FavDB? = null

        @JvmStatic
        fun getDatabase(context: Context): FavDB {
            if (INSTANCE == null) {
                synchronized(FavDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavDB::class.java, "favorite_user_db"
                    )
                        .build()
                }
            }
            return INSTANCE as FavDB
        }
    }
}