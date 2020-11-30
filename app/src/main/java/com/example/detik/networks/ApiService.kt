package com.example.detik.networks

import com.example.detik.models.NewsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("ucnews/listapi.json")
    suspend fun news(): NewsResponse
}