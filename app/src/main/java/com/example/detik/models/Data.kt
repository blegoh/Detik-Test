package com.example.detik.models

import com.google.gson.annotations.SerializedName

data class Data(

    @field:SerializedName("size")
    val size: Int,

    @field:SerializedName("nextPage")
    val nextPage: String,

    @field:SerializedName("rows")
    val rows: List<News>
)