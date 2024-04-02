package com.leftmyhand.githubusers.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.leftmyhand.githubusers.R
import com.leftmyhand.githubusers.data.adapter.SectionsPagerAdapter
import com.leftmyhand.githubusers.data.database.FavUsers
import com.leftmyhand.githubusers.databinding.ActivityDetailBinding
import com.leftmyhand.githubusers.ui.favorite.FavViewModel
import com.leftmyhand.githubusers.ui.favorite.FavViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private var favoriteUser: FavUsers? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favViewModel by viewModels<FavViewModel> {
            FavViewModelFactory.getInstance(this.application)
        }

        favoriteUser = FavUsers()

        val username = intent.getStringExtra(USERNAME)
        if (username != null) {
            viewModel.setLoading(true)
            viewModel.getUserDetail(username)
            viewModel.userDetailLiveData.observe(this) { userDetail ->
                viewModel.setLoading(false)
                showLoading(false)
                binding.fullNameTextView.text = userDetail.name
                binding.usernameTextView.text = userDetail.login

                favoriteUser.let {
                    favoriteUser?.username = userDetail.login.toString()
                    favoriteUser?.avatarUrl = userDetail.avatarUrl.toString()
                }

                val followersCount = userDetail.followers
                val followingCount = userDetail.following

                val followersText = "$followersCount Followers"
                binding.followersLabel.text = followersText

                val followingText = "$followingCount Following"
                binding.followingLabel.text = followingText

                Glide.with(this)
                    .load(userDetail.avatarUrl)
                    .into(binding.userProfileImage)

                favViewModel.isFavorited(username).observe(this) {
                    if (it != null) {
                        binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                        binding.fabFavorite.setOnClickListener {
                            favViewModel.deleteFavUser(username)
                            Toast.makeText(
                                this,
                                "User Deleted From Favorite",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val user = FavUsers(userDetail.login as String, userDetail.avatarUrl)
                        binding.fabFavorite.setImageResource(R.drawable.ic_favoriteborder)
                        binding.fabFavorite.setOnClickListener {
                            favViewModel.insertFavUser(user)
                            Toast.makeText(
                                this,
                                "User Added to Favorites",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

        }

        viewModel.errorLiveData.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username ?: "")
        binding.viewPager2.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_follower)
                1 -> getString(R.string.tab_following)
                else -> ""
            }
        }.attach()
    }

    companion object {
        const val USERNAME = "username_extra"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.viewPager2.visibility = View.VISIBLE
            binding.tabLayout.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}