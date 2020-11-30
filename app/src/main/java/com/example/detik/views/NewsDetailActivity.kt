package com.example.detik.views

import android.os.Bundle
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.detik.R
import com.example.detik.models.News
import com.example.detik.viewmodels.NewsDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_news_detail.*
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val NEWS_KEY = "news"
    }

    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        val news : News? = intent.getParcelableExtra(NEWS_KEY)
        news?.let {
            viewModel.news = it
            val text = "${it.subcategory} - ${it.getPublisHumanTime()}"
            supportActionBar?.title = it.articleFrom
            publishedNews.text = text
            viewModel.updateBookmarkStatus()
            Glide.with(this).load(it.coverPic[0])
                .placeholder(R.drawable.placeholder_list_image)
                .error(R.drawable.placeholder_list_image)
                .centerCrop()
                .into(newsCover)
            newsTitle.text = it.title
            val base64version: String = Base64.encodeToString(it.pages[0].pageContent.toByteArray(), Base64.DEFAULT)
            webView.loadData(base64version, "text/html; charset=UTF-8", "base64")
        }

        viewModel.getMessage().observe(this, {
            if (it.isNotBlank()){
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, it, duration)
                toast.show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_detail_menu, menu)
        viewModel.isBookmarked().observe(this, {
            if (it) {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_bookmark_24
                )
            } else {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_bookmark_border_24
                )
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_bookmark -> {
                viewModel.toggleBookmark()
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}