package com.example.githubapp.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiConfig {

    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeader = req.newBuilder()
                    .addHeader("Authorization", "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")
                    .build()
                chain.proceed(requestHeader)

            }

            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor).build()
            val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            return retrofit.create(ApiService::class.java)
        }
    }
}