package com.example.detik.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: Data
)