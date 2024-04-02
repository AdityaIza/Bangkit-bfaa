package com.leftmyhand.githubusers.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leftmyhand.githubusers.data.database.FavUsers
import com.leftmyhand.githubusers.databinding.ItemUserBinding
import com.leftmyhand.githubusers.ui.detail.DetailActivity

class FavoAdapter(private val listUser: ArrayList<FavUsers>) :
    RecyclerView.Adapter<FavoAdapter.FavoriteUserViewHolder>() {
    inner class FavoriteUserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavUsers) {
            binding.usernameTextView.text = user.username
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.userProfileImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size


    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.USERNAME, user.username)
            holder.itemView.context.startActivity(intent)
        }
    }

}