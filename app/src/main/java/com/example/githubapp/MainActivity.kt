package com.example.githubapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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


        var layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUsers.layoutManager = layoutManager
        val itemDecor = DividerItemDecoration(this, layoutManager.orientation)
        activityMainBinding.rvUsers.addItemDecoration(itemDecor)

        mainViewModel.listUser.observe(this) { listUsers ->
            setData(listUsers)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(activityMainBinding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                mainViewModel.getUsers(searchView.text.toString())
                searchBar.setText(searchView.text)
                searchView.hide()
                Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                false
            }
        }


    }

    private fun setData(listUsers: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(listUsers)
        activityMainBinding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            activityMainBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityMainBinding.progressBar.visibility = View.INVISIBLE
        }
    }
}