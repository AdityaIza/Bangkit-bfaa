package com.leftmyhand.githubusers.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.leftmyhand.githubusers.data.adapter.UserAdapter
import com.leftmyhand.githubusers.databinding.FragmentFollowersFollowingBinding

class FollowersFollowingFragment : Fragment() {
    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION) ?: 1
        val username = arguments?.getString(ARG_USERNAME) ?: ""

        val recyclerView = binding.recyclerViewfoll
        userAdapter = UserAdapter(requireContext())

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        if (position == 1) {
            viewModel.getFollowers(username)
            showLoading(true)
            viewModel.followersLiveData.observe(viewLifecycleOwner) { followersList ->
                userAdapter.submitList(followersList)
                showLoading(false)
            }
        } else {
            viewModel.getFollowing(username)
            showLoading(true)
            viewModel.followingLiveData.observe(viewLifecycleOwner) { followingList ->
                userAdapter.submitList(followingList)
                showLoading(false)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerViewfoll.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewfoll.visibility = View.VISIBLE
        }
    }
}