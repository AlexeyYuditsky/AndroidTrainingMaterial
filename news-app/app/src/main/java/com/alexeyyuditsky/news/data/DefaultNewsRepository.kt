package com.alexeyyuditsky.news.data

import com.alexeyyuditsky.news.data.retrofit.NewsApi
import com.alexeyyuditsky.news.data.room.ArticleDao
import com.alexeyyuditsky.news.domain.NewsRepository
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.data.retrofit.NewsResponse
import retrofit2.Response

class DefaultNewsRepository(
    private val articleDao: ArticleDao,
    private val newsApi: NewsApi
) : NewsRepository {

    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return newsApi.getBreakingNews(countryCode, pageNumber)
    }

    override suspend fun saveArticle(article: Article) = articleDao.saveArticle(article)

    override suspend fun deleteArticle(article: Article) = articleDao.deleteArticle(article)

    override fun getSavedNews() = articleDao.getAllArticles()

}