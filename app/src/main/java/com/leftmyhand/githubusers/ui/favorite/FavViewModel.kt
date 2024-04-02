package com.leftmyhand.githubusers.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.leftmyhand.githubusers.data.database.FavUsers
import com.leftmyhand.githubusers.data.repository.FavoRepository

class FavViewModel(private val favoriteRepository: FavoRepository) : ViewModel() {

    fun getAllFavorite(): LiveData<List<FavUsers>> = favoriteRepository.getAllFavorite()

    fun isFavorited(username: String): LiveData<FavUsers> =
        favoriteRepository.isFavorited(username)

    fun insertFavUser(favoriteUser: FavUsers) {
        favoriteRepository.insertFavUser(favoriteUser)
    }

    fun deleteFavUser(username: String) {
        favoriteRepository.deleteFavUser(username)
    }
}