package com.example.githubapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.data.response.GithubResponse
import com.example.githubapp.data.response.ItemsItem
import com.example.githubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        getUsers()
    }

    private fun getUsers() {
        Log.d(TAG, "GET USERS")
        val client = ApiConfig.getApiService().getUsers("lutfi")
        client.enqueue(object : retrofit2.Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                    Log.d(TAG, listUser.value?.size.toString())
                } else {
                    Log.d(TAG, "OnFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.d(TAG, "OnFailure : ${t.message}")
            }
        })
    }
}