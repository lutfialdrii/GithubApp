package com.example.githubapp.data.retrofit

import com.example.githubapp.data.response.DetailUserResponse
import com.example.githubapp.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(@Query("q") q: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

}