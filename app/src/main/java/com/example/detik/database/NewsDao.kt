package com.example.detik.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.detik.models.News

@Dao
interface  NewsDao {

    @Query("SELECT * FROM news")
    suspend fun getAll(): List<News>

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun findById(id : Int): List<News>

    @Insert
    suspend fun insertAll(vararg news: News)

    @Delete
    suspend fun delete(news: News)

}