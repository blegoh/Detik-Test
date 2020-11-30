package com.example.detik.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detik.NewsRepository
import com.example.detik.models.News
import kotlinx.coroutines.launch

class BookmarkViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var message: MutableLiveData<String> = MutableLiveData("")
    private var news: MutableLiveData<List<News>> = MutableLiveData()

    fun getNews(): LiveData<List<News>> = news
    fun getMessage(): LiveData<String> = message
    fun isLoading(): LiveData<Boolean> = loading

    fun updateNews() = viewModelScope.launch {
        loading.value = true
        news.value = newsRepository.getBookmarks()
        loading.value = false
    }

    fun toggleBookmark(news: News) = viewModelScope.launch {
        val bookmarkCheck = newsRepository.getBookmark(news.id)
        if (bookmarkCheck.size > 0) {
            newsRepository.removeFromBookmarks(news)
            message.value = "Removed from bookmarks"
        } else {
            newsRepository.addToBookmarks(news)
            message.value = "Added to bookmarks"
        }
        updateNews()
    }
}