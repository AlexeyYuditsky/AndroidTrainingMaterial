package com.alexeyyuditsky.news.domain

import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.data.retrofit.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>

    suspend fun saveArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getSavedNews(): Flow<List<Article>>

}