package com.leftmyhand.githubusers.data.retrofit

import com.leftmyhand.githubusers.data.response.DetailUsersResponse
import com.leftmyhand.githubusers.data.response.ItemsItem
import com.leftmyhand.githubusers.data.response.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getsearchUser(@Query("q") query: String): Call<UsersResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUsersResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getfollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getfollowing(@Path("username") username: String): Call<List<ItemsItem>>
}