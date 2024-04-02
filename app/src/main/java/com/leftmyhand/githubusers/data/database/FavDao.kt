package com.leftmyhand.githubusers.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavUsers)

    @Query("DELETE FROM favorite_user WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT * from favorite_user")
    fun getAllFavorite(): LiveData<List<FavUsers>>

    @Query("SELECT * FROM favorite_user WHERE username = :username")
    fun isFavorite(username: String): LiveData<FavUsers>
}