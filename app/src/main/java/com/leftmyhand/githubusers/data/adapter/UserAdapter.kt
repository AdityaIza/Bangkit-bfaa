package com.leftmyhand.githubusers.data.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leftmyhand.githubusers.data.response.ItemsItem
import com.leftmyhand.githubusers.databinding.ItemUserBinding
import com.leftmyhand.githubusers.ui.detail.DetailActivity

class UserAdapter(private val context: Context) :
    ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(UserDiffCallback()) {

    class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.usernameTextView.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.userProfileImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.USERNAME, user.login)
            context.startActivity(intent)
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<ItemsItem>() {
    override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
        return oldItem == newItem
    }
}