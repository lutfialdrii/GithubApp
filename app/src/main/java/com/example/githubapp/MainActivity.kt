package com.example.githubapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.data.response.ItemsItem
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.ui.ListUserAdapter
import com.example.githubapp.ui.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUsers.layoutManager = layoutManager
        val itemDecor = DividerItemDecoration(this, layoutManager.orientation)
        activityMainBinding.rvUsers.addItemDecoration(itemDecor)

        mainViewModel.listUser.observe(this) { listUsers ->
            setReviewData(listUsers)
        }


    }

    private fun setReviewData(listUsers: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(listUsers)
        activityMainBinding.rvUsers.adapter = adapter
    }
}