package com.example.android.codelabs.paging.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.model.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

const val DEFAULT_QUERY = "Android"

@FlowPreview
@ExperimentalCoroutinesApi
class RepositoriesViewModel(
    private val repository: GithubRepository,
    savedStateHandle: SavedStateHandle,
    application: Application,
) : AndroidViewModel(application) {

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
        val repoPagingFlow: Flow<PagingData<Repo>> = repository.getSearchResultStream(queryString)

        val repoItemPagingFlow: Flow<PagingData<UiModel.RepoItem>> = repoPagingFlow
            .map { pagingData: PagingData<Repo> -> pagingData.map { repo: Repo -> UiModel.RepoItem(repo) } }

        val uiModelPagingFlow: Flow<PagingData<UiModel>> = repoItemPagingFlow
            .map { pagingData: PagingData<UiModel.RepoItem> ->
                val application = getApplication<Application>()
                pagingData.insertSeparators { before: UiModel.RepoItem?, current: UiModel.RepoItem? ->
                    if (current == null) {
                        return@insertSeparators null
                    }
                    if (before == null) {
                        if (current.repo.stars >= 10_000) {
                            val description = application.getString(R.string.desc_stars, current.roundedStarCount)
                            return@insertSeparators UiModel.SeparatorItem(description)
                        } else {
                            val description = application.getString(R.string.desc_stars_plus, current.repo.stars + 1)
                            return@insertSeparators UiModel.SeparatorItem(description)
                        }
                    }
                    if (before.roundedStarCount > current.roundedStarCount) {
                        if (current.roundedStarCount >= 1) {
                            val description = application.getString(R.string.desc_stars, current.roundedStarCount)
                            UiModel.SeparatorItem(description)
                        } else {
                            val description = application.getString(R.string.desc_stars_other)
                            UiModel.SeparatorItem(description)
                        }
                    } else {
                        return@insertSeparators null
                    }
                }
            }
        return uiModelPagingFlow
    }

}

sealed class UiModel {
    data class RepoItem(val repo: Repo) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}

private val UiModel.RepoItem.roundedStarCount: Int
    get() = this.repo.stars / 10_000