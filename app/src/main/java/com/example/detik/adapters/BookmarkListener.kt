package com.example.detik.adapters

import com.example.detik.models.News

interface BookmarkListener {
    fun onToggleBookmark(news : News)
}