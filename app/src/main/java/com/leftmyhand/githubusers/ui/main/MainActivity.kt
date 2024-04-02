package com.leftmyhand.githubusers.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.leftmyhand.githubusers.R
import com.leftmyhand.githubusers.data.adapter.UserAdapter
import com.leftmyhand.githubusers.databinding.ActivityMainBinding
import com.leftmyhand.githubusers.ui.favorite.FavActivity
import com.leftmyhand.githubusers.ui.theme.MainViewModel
import com.leftmyhand.githubusers.ui.theme.SettingPreferences
import com.leftmyhand.githubusers.ui.theme.ThemeActivity
import com.leftmyhand.githubusers.ui.theme.ViewModelFactory
import com.leftmyhand.githubusers.ui.theme.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(this)
        binding.recyclerViewUser.adapter = userAdapter
        binding.recyclerViewUser.layoutManager = LinearLayoutManager(this)

        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = textView.text.toString()
                viewModel.searchUsers(query)
                showLoading(true)
                binding.searchView.hide()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.searchBar.inflateMenu(R.menu.main_menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuFav -> {
                    val intent = Intent(this, FavActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menuSet -> {
                    val intent = Intent(this, ThemeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        viewModel.getUsersLiveData().observe(this) { githubResponse ->
            showLoading(false)
            githubResponse?.let {
                userAdapter.submitList(it)
            }
            Log.d("TAG", "response: $githubResponse")
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerViewUser.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewUser.visibility = View.VISIBLE
        }
    }
}