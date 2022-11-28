package com.example.android.codelabs.paging.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.model.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val DEFAULT_QUERY = "Android"
private const val LAST_SEARCH_QUERY = "last_search_query"

@FlowPreview
@ExperimentalCoroutinesApi
class SearchRepositoriesViewModel(
    private val repository: GithubRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<Repo>>

    val state: StateFlow<UiState>

    val accept: (UiState) -> Unit

    init {
        val initialQuery = savedStateHandle[LAST_SEARCH_QUERY] ?: DEFAULT_QUERY

        val actionStateFlow = MutableSharedFlow<UiState>()

        val searches: Flow<UiState> = actionStateFlow
            .onStart { emit(UiState(query = initialQuery)) }

        pagingDataFlow = searches
            .flatMapLatest { search: UiState -> searchRepo(queryString = search.query) }
            .cachedIn(viewModelScope)

        state = searches
            .map { search: UiState -> UiState(query = search.query) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UiState()
            )

        accept = { action: UiState ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        super.onCleared()
    }

    private fun searchRepo(queryString: String): Flow<PagingData<Repo>> {
        return repository.getSearchResultStream(queryString)
    }

}