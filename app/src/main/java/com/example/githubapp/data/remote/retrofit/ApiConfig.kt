package com.example.githubapp.data.remote.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeader = req.newBuilder()
                    .addHeader("Authorization", "token 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")
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