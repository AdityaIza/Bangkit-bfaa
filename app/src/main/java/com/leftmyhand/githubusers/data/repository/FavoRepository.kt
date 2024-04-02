package com.leftmyhand.githubusers.data.repository

import androidx.lifecycle.LiveData
import com.leftmyhand.githubusers.data.database.FavDao
import com.leftmyhand.githubusers.data.database.FavUsers
import com.leftmyhand.githubusers.ui.utils.AppExe

class FavoRepository private constructor(
    private val databaseDao: FavDao,
    private val appExecutors: AppExe
) {
    fun isFavorited(username: String): LiveData<FavUsers> =
        databaseDao.isFavorite(username)

    fun getAllFavorite(): LiveData<List<FavUsers>> = databaseDao.getAllFavorite()
    fun insertFavUser(favoriteUser: FavUsers) {
        appExecutors.diskIO.execute { databaseDao.insert(favoriteUser) }
    }

    fun deleteFavUser(username: String) {
        appExecutors.diskIO.execute { databaseDao.delete(username) }
    }

    companion object {
        @Volatile
        private var instance: FavoRepository? = null
        fun getInstance(
            databaseDao: FavDao,
            appExecutors: AppExe
        ): FavoRepository =
            instance ?: synchronized(this) {
                instance ?: FavoRepository(databaseDao, appExecutors)
            }.also { instance = it }
    }
}