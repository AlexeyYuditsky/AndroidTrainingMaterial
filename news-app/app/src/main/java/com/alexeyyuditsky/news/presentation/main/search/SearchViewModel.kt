package com.alexeyyuditsky.news.presentation.main.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.domain.PagingNewsRepository
import com.alexeyyuditsky.news.presentation.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

const val DEFAULT_QUERY = "news"

@ExperimentalCoroutinesApi
@FlowPreview
class SearchViewModel(
    private val repository: PagingNewsRepository
) : BaseViewModel() {

    val pagingDataFlow: Flow<PagingData<Article>>

    val accept: (String) -> Unit

    init {
        val actionStateFlow = MutableStateFlow(DEFAULT_QUERY)

        val searches: Flow<String> = actionStateFlow
            .debounce(1000)
            .filter { it.isNotBlank() }

        pagingDataFlow = searches
            .flatMapLatest { query: String -> searchArticles(query = query) }
            .cachedIn(viewModelScope)

        accept = { query: String -> actionStateFlow.tryEmit(query) }
    }

    private fun searchArticles(query: String): Flow<PagingData<Article>> {
        return repository.getSearchNews(query)
    }

}