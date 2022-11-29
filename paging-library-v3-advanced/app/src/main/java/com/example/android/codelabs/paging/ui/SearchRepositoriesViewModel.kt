package com.example.android.codelabs.paging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
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

    val pagingDataFlow: Flow<PagingData<UiModel>>

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

    private fun searchRepo(queryString: String): Flow<PagingData<UiModel>> {
        return repository.getSearchResultStream(queryString)
            .map { pagingData -> pagingData.map { repo -> UiModel.RepoItem(repo) } }
            .map { pagingData ->
                pagingData.insertSeparators { before, after ->
                    if (after == null) {
                        return@insertSeparators null
                    }
                    if (before == null) {
                        return@insertSeparators UiModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                    }
                    if (before.roundedStarCount > after.roundedStarCount) {
                        if (after.roundedStarCount >= 1) {
                            UiModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                        } else {
                            UiModel.SeparatorItem("< 10.000+ stars")
                        }
                    } else {
                        null
                    }
                }
            }
    }

}

sealed class UiModel {
    data class RepoItem(val repo: Repo) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}

private val UiModel.RepoItem.roundedStarCount: Int
    get() = this.repo.stars / 10_000