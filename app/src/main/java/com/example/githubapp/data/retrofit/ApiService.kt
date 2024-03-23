package com.example.githubapp.data.retrofit

import com.example.githubapp.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(@Query("q") q: String): Call<GithubResponse>
}