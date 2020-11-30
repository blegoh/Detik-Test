package com.example.detik.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PagesItem(

    @field:SerializedName("pageNumber")
    val pageNumber: Int,

    @field:SerializedName("pageContent")
    val pageContent: String
) : Parcelable