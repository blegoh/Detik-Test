package com.example.detik.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity
data class News(

    @field:SerializedName("summary")
    @ColumnInfo(name = "summary")
    val summary: String,

    @field:SerializedName("publishTime")
    @ColumnInfo(name = "publish_time")
    val publishTime: Long,

    @field:SerializedName("city")
    @ColumnInfo(name = "city")
    val city: String,

    @field:SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String,

    @field:SerializedName("language")
    @ColumnInfo(name = "language")
    val language: String,

    @field:SerializedName("originalUrl")
    @ColumnInfo(name = "original_url")
    val originalUrl: String,

    @field:SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String,

    @field:SerializedName("tags")
    @ColumnInfo(name = "tags")
    val tags: List<String>,

    @field:SerializedName("pages")
    @ColumnInfo(name = "pages")
    val pages: List<PagesItem>,

    @field:SerializedName("province")
    @ColumnInfo(name = "province")
    val province: String,

    @field:SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @field:SerializedName("detailUrl")
    @ColumnInfo(name = "detail_url")
    val detailUrl: String,

    @field:SerializedName("coverPic")
    @ColumnInfo(name = "cover_pic")
    val coverPic: List<String>,

    @field:SerializedName("category")
    @ColumnInfo(name = "category")
    val category: String,

    @field:SerializedName("subcategory")
    @ColumnInfo(name = "subcategory")
    val subcategory: String,

    @field:SerializedName("keyword")
    @ColumnInfo(name = "keyword")
    val keyword: String,

    @field:SerializedName("articleFrom")
    @ColumnInfo(name = "article_from")
    val articleFrom: String
) : Parcelable {
    fun getPublisHumanTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = Date(this.publishTime * 1000)
        return sdf.format(date)
    }
}