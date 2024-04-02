package com.leftmyhand.githubusers.data.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.leftmyhand.githubusers.ui.detail.FollowersFollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return FollowersFollowingFragment().apply {
            arguments = Bundle().apply {
                putInt("position", position + 1)
                putString("username", username)
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
