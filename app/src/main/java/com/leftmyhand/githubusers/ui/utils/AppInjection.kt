package com.leftmyhand.githubusers.ui.utils

import android.content.Context
import com.leftmyhand.githubusers.data.database.FavDB
import com.leftmyhand.githubusers.data.repository.FavoRepository

object AppInjection {
    fun provideRepository(context: Context): FavoRepository {
        val database = FavDB.getDatabase(context)
        val dao = database.favDao()
        val appExecutors = AppExe()
        return FavoRepository.getInstance(dao, appExecutors)
    }
}