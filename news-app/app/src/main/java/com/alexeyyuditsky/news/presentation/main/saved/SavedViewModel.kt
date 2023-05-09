package com.alexeyyuditsky.news.presentation.main.saved

import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.domain.NewsRepository
import com.alexeyyuditsky.news.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SavedViewModel(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.saveArticle(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

}