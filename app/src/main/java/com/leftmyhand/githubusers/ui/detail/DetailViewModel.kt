package com.leftmyhand.githubusers.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leftmyhand.githubusers.data.response.DetailUsersResponse
import com.leftmyhand.githubusers.data.response.ItemsItem
import com.leftmyhand.githubusers.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val _userDetailLiveData = MutableLiveData<DetailUsersResponse>()
    val userDetailLiveData: LiveData<DetailUsersResponse> = _userDetailLiveData

    private val _followersLiveData = MutableLiveData<List<ItemsItem>>()
    val followersLiveData: LiveData<List<ItemsItem>> = _followersLiveData

    private val _followingLiveData = MutableLiveData<List<ItemsItem>>()
    val followingLiveData: LiveData<List<ItemsItem>> = _followingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun getUserDetail(username: String) {
        apiService.getDetailUser(username).enqueue(object : Callback<DetailUsersResponse> {
            override fun onResponse(
                call: Call<DetailUsersResponse>,
                response: Response<DetailUsersResponse>
            ) {
                if (response.isSuccessful) {
                    _userDetailLiveData.value = response.body()
                } else {
                    _errorLiveData.value = "Failed to load user details"
                }
            }

            override fun onFailure(call: Call<DetailUsersResponse>, t: Throwable) {
                _errorLiveData.value = "Failure to load"
            }
        })
    }

    fun getFollowers(username: String) {
        apiService.getfollowers(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _followersLiveData.value = response.body()
                } else {
                    _errorLiveData.value = "Failed to load the Followers list"
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _errorLiveData.value = "Failed to load the Followers list"
            }
        })
    }

    fun getFollowing(username: String) {
        apiService.getfollowing(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _followingLiveData.value = response.body()
                } else {
                    _errorLiveData.value = "Failed to load the Following list"
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _errorLiveData.value = "Failed to load the Following list"
            }
        })
    }

}