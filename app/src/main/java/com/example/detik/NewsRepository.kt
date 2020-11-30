package com.example.detik

import com.example.detik.database.AppDatabase
import com.example.detik.models.News
import com.example.detik.networks.ApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val service: ApiService,
    private val db: AppDatabase
) {

    suspend fun getNews() = service.news()

    suspend fun getBookmarks() = db.newsDao().getAll()

    suspend fun getBookmark(id : Int) = db.newsDao().findById(id)

    suspend fun addToBookmarks(news: News) = db.newsDao().insertAll(news)

    suspend fun removeFromBookmarks(news: News) = db.newsDao().delete(news)
}