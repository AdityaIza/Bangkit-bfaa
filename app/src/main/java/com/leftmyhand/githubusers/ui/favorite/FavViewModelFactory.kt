package com.leftmyhand.githubusers.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leftmyhand.githubusers.data.repository.FavoRepository
import com.leftmyhand.githubusers.ui.utils.AppInjection

class FavViewModelFactory private constructor(
    private val favoriteRepository: FavoRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavViewModelFactory? = null
        fun getInstance(application: Application): FavViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavViewModelFactory(AppInjection.provideRepository(application))
            }.also { instance = it }
    }
}