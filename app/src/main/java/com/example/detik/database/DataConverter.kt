package com.example.detik.database

import androidx.room.TypeConverter
import com.example.detik.models.PagesItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DataConverter {

    @TypeConverter
    fun fromStringList(tags: List<String>?): String? {
        val gson = Gson()
        tags?.let{
            val json = gson.toJson(tags)
            return json
        }
        return null
    }

    @TypeConverter
    fun fromPageList(pages: List<PagesItem>?): String? {
        val gson = Gson()
        pages?.let{
            val type: Type = object : TypeToken<List<PagesItem>>() {}.type
            val json = gson.toJson(pages,type)
            return json
        }
        return null
    }

    @TypeConverter
    fun toStringList(json: String?): List<String>? {
        val gson = Gson()
        json?.let{
            val type: Type = object : TypeToken<List<String>>() {}.type
            val tags = gson.fromJson<List<String>>(json, type)
            return tags
        }
        return null
    }

    @TypeConverter
    fun toPageList(json: String?): List<PagesItem>? {
        val gson = Gson()
        json?.let {
            val type: Type = object : TypeToken<List<PagesItem>>() {}.type
            val pages = gson.fromJson<List<PagesItem>>(json, type)
            return pages
        }
        return null
    }
}