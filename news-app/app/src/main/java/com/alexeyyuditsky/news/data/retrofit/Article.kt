package com.alexeyyuditsky.news.data.retrofit

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey
    val title: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: @RawValue Source?,
    val url: String?,
    val urlToImage: String?
) : Parcelable