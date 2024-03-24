package com.example.githubapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.data.response.DetailUserResponse
import com.example.githubapp.databinding.ActivityDetailUserBinding
import com.example.githubapp.utils.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailViewModel>()

    companion object {
        const val KEY_NAME = "username"
        private val TAB_TITLES =
            arrayOf("Followers", "Following")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)

        val username = intent.getStringExtra(KEY_NAME)
        Log.d(KEY_NAME, username!!)

        detailUserViewModel.getUser(username)
        detailUserViewModel.detailUser.observe(this) {
            setData(it)
        }
        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

//        Menghubungkan ViewPager2 dengan TabLayout
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager2: ViewPager2 = detailUserBinding.viewPager
        viewPager2.adapter = sectionsPagerAdapter

        val tabs: TabLayout = detailUserBinding.tabs
        TabLayoutMediator(tabs, viewPager2) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

    }

    private fun setData(user: DetailUserResponse) {
//        Log.d("userData", user.toString())
        Glide.with(this).load(user.avatarUrl).into(detailUserBinding.ivAvatar)
        detailUserBinding.tvName.text = user.name
        detailUserBinding.tvUsername.text = user.login
        detailUserBinding.tvBio.text = user.bio
        val newFollowers = this.resources.getString(R.string.followers, user.followers)
        val newFollowing = this.resources.getString(R.string.following, user.following)
        detailUserBinding.tvFollowers.text = newFollowers
        detailUserBinding.tvFollowing.text = newFollowing
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            detailUserBinding.progressBar.visibility = View.VISIBLE
        } else {
            detailUserBinding.progressBar.visibility = View.INVISIBLE
        }
    }
}