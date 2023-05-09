package com.alexeyyuditsky.news.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alexeyyuditsky.news.data.paging.PagingNewsSource
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.data.retrofit.NewsApi
import com.alexeyyuditsky.news.domain.PagingNewsRepository
import kotlinx.coroutines.flow.Flow

const val NETWORK_PAGE_SIZE = 15

class DefaultPagingNewsRepository(
    private val newsApi: NewsApi
) : PagingNewsRepository {

    override fun getSearchNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagingNewsSource(newsApi, query) }
        ).flow
    }

}