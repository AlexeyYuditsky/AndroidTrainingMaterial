package com.example.android.codelabs.paging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.model.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

const val DEFAULT_QUERY = "Android"

@FlowPreview
@ExperimentalCoroutinesApi
class SearchRepositoriesViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<Repo>>

    val accept: (String) -> Unit

    init {
        val actionStateFlow = MutableStateFlow(DEFAULT_QUERY)

        val searches: Flow<String> = actionStateFlow
            .debounce(1000)
            .filter { it.isNotBlank() }

        pagingDataFlow = searches
            .flatMapLatest { query: String -> searchRepo(queryString = query) }
            .cachedIn(viewModelScope)

        accept = { query: String -> actionStateFlow.tryEmit(query) }
    }

    private fun searchRepo(queryString: String): Flow<PagingData<Repo>> {
        return repository.getSearchResultStream(queryString)
    }

}