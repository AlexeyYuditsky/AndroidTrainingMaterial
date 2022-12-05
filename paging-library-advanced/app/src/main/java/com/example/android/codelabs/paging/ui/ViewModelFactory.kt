package com.example.android.codelabs.paging.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.android.codelabs.paging.data.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class ViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: GithubRepository,
    private val application: Application
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        @Suppress("UNCHECKED_CAST")
        return RepositoriesViewModel(repository, handle, application) as T
    }

}
