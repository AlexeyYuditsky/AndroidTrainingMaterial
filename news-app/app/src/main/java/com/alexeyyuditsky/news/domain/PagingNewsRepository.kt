package com.alexeyyuditsky.news.domain

import androidx.paging.PagingData
import com.alexeyyuditsky.news.data.retrofit.Article
import kotlinx.coroutines.flow.Flow

interface PagingNewsRepository {

    fun getSearchNews(query: String): Flow<PagingData<Article>>

}