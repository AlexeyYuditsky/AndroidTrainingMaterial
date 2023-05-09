package com.alexeyyuditsky.news.data.retrofit

data class NewsResponse(
    val articles: MutableList<Article> = mutableListOf(),
    val totalResults: Int
)