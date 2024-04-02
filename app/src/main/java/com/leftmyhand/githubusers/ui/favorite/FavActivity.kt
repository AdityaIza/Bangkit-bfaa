package com.leftmyhand.githubusers.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.leftmyhand.githubusers.data.adapter.FavoAdapter
import com.leftmyhand.githubusers.databinding.ActivityFavoriteBinding

class FavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favviewModel by viewModels<FavViewModel> {
            FavViewModelFactory.getInstance(this.application)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFav.layoutManager = layoutManager

        favviewModel.getAllFavorite().observe(this) { listUser ->
            binding.recyclerViewFav.adapter = FavoAdapter(ArrayList(listUser))
        }
    }
}