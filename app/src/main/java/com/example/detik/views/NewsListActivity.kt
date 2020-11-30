package com.example.detik.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
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
import com.example.detik.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_news_list.*

@AndroidEntryPoint
class NewsListActivity : AppCompatActivity() {

    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter(object: BookmarkListener{
        override fun onToggleBookmark(news: News) {
            viewModel.toggleBookmark(news)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel.getNews().observe(this, {
            newsAdapter.news = it
        })
        viewModel.getBookmarks().observe(this, {
            newsAdapter.bookmarks = it
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
        viewModel.updateBookmarks()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = newsAdapter

        swipeRefresh.setOnRefreshListener {
            viewModel.updateNews()
            viewModel.updateBookmarks()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bookmarks -> {
                val intent = Intent(this, BookmarkActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}