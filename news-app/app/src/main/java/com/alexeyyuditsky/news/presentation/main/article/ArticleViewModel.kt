package com.alexeyyuditsky.news.presentation.main.article

import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.presentation.base.BaseViewModel
import com.alexeyyuditsky.news.domain.NewsRepository
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.saveArticle(article)
    }

}