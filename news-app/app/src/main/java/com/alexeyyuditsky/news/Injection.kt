package com.alexeyyuditsky.news

import android.content.Context
import androidx.room.Room
import com.alexeyyuditsky.news.data.DefaultNewsRepository
import com.alexeyyuditsky.news.data.DefaultPagingNewsRepository
import com.alexeyyuditsky.news.data.retrofit.NewsApi
import com.alexeyyuditsky.news.data.room.AppDatabase
import com.alexeyyuditsky.news.domain.NewsRepository
import com.alexeyyuditsky.news.domain.PagingNewsRepository

object Injection {

    private lateinit var appContext: Context

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(appContext.applicationContext, AppDatabase::class.java, "articles.db").build()
    }

    private val newsApi: NewsApi by lazy {
        NewsApi.create()
    }

    val newsRepository: NewsRepository by lazy {
        DefaultNewsRepository(database.getArticleDao(), newsApi)
    }

    val pagingNewsRepository: PagingNewsRepository by lazy {
        DefaultPagingNewsRepository(newsApi)
    }

    fun init(appContext: Context) {
        this.appContext = appContext
    }

}