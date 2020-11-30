package com.example.detik.views

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.detik.R
import com.example.detik.adapters.BookmarkListener
import com.example.detik.adapters.NewsAdapter
import com.example.detik.models.News
import com.example.detik.viewmodels.BookmarkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_news_list.*

@AndroidEntryPoint
class BookmarkActivity : AppCompatActivity() {

    private val viewModel: BookmarkViewModel by viewModels()
    private val newsAdapter = NewsAdapter(object: BookmarkListener {
        override fun onToggleBookmark(news: News) {
            viewModel.toggleBookmark(news)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        newsAdapter.isBookmarPage = true
        viewModel.getNews().observe(this, {
            newsAdapter.news = it
        })
        viewModel.isLoading().observe(this, {
            swipeRefresh.isRefreshing = it
        })
        viewModel.getMessage().observe(this, {
            if (it.isNotBlank()){
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, it, duration)
                toast.show()
            }
        })
        viewModel.updateNews()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = newsAdapter

        swipeRefresh.setOnRefreshListener {
            viewModel.updateNews()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

}