package com.example.detik.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detik.NewsRepository
import com.example.detik.models.News
import kotlinx.coroutines.launch

class NewsDetailViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var bookmarked: MutableLiveData<Boolean> = MutableLiveData(false)
    private var message: MutableLiveData<String> = MutableLiveData("")

    fun isBookmarked(): LiveData<Boolean> = bookmarked
    fun getMessage(): LiveData<String> = message

    var news: News? = null

    fun updateBookmarkStatus()= viewModelScope.launch {
        news?.let {
            val news = newsRepository.getBookmark(it.id)
            bookmarked.value = news.isNotEmpty()
        }
    }

    fun toggleBookmark() = viewModelScope.launch{
        updateBookmarkStatus()
        news?.let {
            if (bookmarked.value!!){
                newsRepository.removeFromBookmarks(it)
                message.value = "Removed from bookmarks"
            }else{
                newsRepository.addToBookmarks(it)
                message.value = "Added to bookmarks"
            }
            bookmarked.value = !bookmarked.value!!
        }

    }
}