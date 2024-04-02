package com.leftmyhand.githubusers.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leftmyhand.githubusers.data.response.ItemsItem
import com.leftmyhand.githubusers.data.response.UsersResponse
import com.leftmyhand.githubusers.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    companion object{
        private const val TAG = "UserViewModel"
    }

    private val apiService = ApiConfig.getApiService()
    private val userListLiveData = MutableLiveData<List<ItemsItem>>()
    private val _users = MutableLiveData<List<ItemsItem>>()
    private val _error = MutableLiveData<String>()
    val users: LiveData<List<ItemsItem>> = MutableLiveData()

    init {
        searchUsers("AdityaI")
    }

    fun searchUsers(query: String) {
        apiService.getsearchUser(query).enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful) {
                    userListLiveData.value = response.body()?.items as List<ItemsItem>
                }
                else {
                    Log.e(TAG, "Error response body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e(TAG,"Error: ${t.message}", t)
            }
        })
    }

    fun getUsersLiveData(): LiveData<List<ItemsItem>> {
        return userListLiveData
    }
    fun getFollowers(username: String) {
        apiService.getfollowers(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                if (response.isSuccessful) { response.body()?.let {
                    _users.postValue(it)
                }
                } else {
                    _error.postValue("Failed to fetch followers. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _error.postValue("Network error: ${t.message}")
            }
        })
    }

    fun getFollowing(username: String) {
        apiService.getfollowing(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                if (response.isSuccessful) { response.body()?.let {
                    _users.postValue(it)
                }
                } else {
                    _error.postValue("Failed to fetch following. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _error.postValue("Network error: ${t.message}")
            }
        })
    }
}
