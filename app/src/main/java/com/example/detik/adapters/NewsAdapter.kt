package com.example.detik.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.detik.OnSwipeTocuhListener
import com.example.detik.R
import com.example.detik.models.News
import com.example.detik.views.NewsDetailActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_item.*

class NewsAdapter(
    private val bookmarkListener: BookmarkListener
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var news: List<News> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var bookmarks: List<News> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var isBookmarPage: Boolean = false

    private fun isBookmarked(position: Int): Boolean {
        val news = this.news[position]
        val filteredBookmarks = bookmarks.filter {
            it.id == news.id
        }
        return filteredBookmarks.size > 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val status: Boolean
        if (isBookmarPage) {
            status = true
        } else {
            status = isBookmarked(position)
        }
        holder.bindItem(news[position], status, this.bookmarkListener)
    }

    override fun getItemCount() = news.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(news: News, isBookmarked: Boolean, bookmarkListener: BookmarkListener) {
            updateIcon(isBookmarked)
            newsTitle.text = news.title
            publishedNews.text = news.getPublisHumanTime()
            var isShowed = false
            Glide.with(containerView.context).load(news.coverPic[0])
                .centerCrop()
                .placeholder(R.drawable.placeholder_list_image)
                .error(R.drawable.placeholder_list_image)
                .into(imageView)
            detail.setOnClickListener {
                goToDetail(news)
            }
            bookmarkStatus.setOnClickListener {
                updateIcon(!isBookmarked)
                bookmarkListener.onToggleBookmark(news)
            }
            div.setOnClickListener { goToDetail(news) }
//            div.setOnTouchListener(touchListener)
//            newsTitle.setOnClickListener { goToDetail(news) }
            newsTitle.setOnTouchListener(touchListener)
            publishedNews.setOnTouchListener(touchListener)
            subtitle.setOnTouchListener(touchListener)
            containerView.setOnClickListener {
                goToDetail(news)
            }
        }

        private fun goToDetail(news: News) {
            val intent = Intent(containerView.context, NewsDetailActivity::class.java)
            intent.putExtra(NewsDetailActivity.NEWS_KEY, news)
            containerView.context.startActivity(intent)
        }

        private fun updateIcon(isBookmarked: Boolean) {
            if (isBookmarked)
                updateBookmarkIcon(R.drawable.bookmarked)
            else
                updateBookmarkIcon(R.drawable.bookmark)
        }

        private fun updateBookmarkIcon(icon: Int) {
            bookmarkStatus.setImageDrawable(
                containerView.context.resources.getDrawable(
                    icon,
                    containerView.context.theme
                )
            )
        }

        private val touchListener = object : OnSwipeTocuhListener(containerView.context) {
            override fun onSwipeRight() {
                bookmarkStatus.visibility = View.VISIBLE
                verticalLine.visibility = View.VISIBLE
            }

            override fun onSwipeLeft() {
                bookmarkStatus.visibility = View.GONE
                verticalLine.visibility = View.GONE
            }
        }
    }

}