package com.example.detik.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detik.NewsRepository
import com.example.detik.models.News
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var message: MutableLiveData<String> = MutableLiveData("")
    private var news: MutableLiveData<List<News>> = MutableLiveData()
    private var bookmarks: MutableLiveData<List<News>> = MutableLiveData()

    fun isLoading(): LiveData<Boolean> = loading
    fun getMessage(): LiveData<String> = message
    fun getNews(): LiveData<List<News>> = news
    fun getBookmarks(): LiveData<List<News>> = bookmarks

    fun updateNews() = viewModelScope.launch {
        loading.value = true
        try {
            val res = newsRepository.getNews()
            news.value = res.data.rows
        } catch (ex: Exception) {
            message.value = ex.message
        }
        loading.value = false
    }

    fun updateBookmarks() = viewModelScope.launch {
        bookmarks.value = newsRepository.getBookmarks()
    }

    fun toggleBookmark(news: News) = viewModelScope.launch {
        val bookmarkCheck = newsRepository.getBookmark(news.id)
        if (bookmarkCheck.isNotEmpty()) {
            newsRepository.removeFromBookmarks(news)
            message.value = "Removed from bookmarks"
        } else {
            newsRepository.addToBookmarks(news)
            message.value = "Added to bookmarks"
        }
    }
}