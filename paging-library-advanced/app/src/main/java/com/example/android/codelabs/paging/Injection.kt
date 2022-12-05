package com.example.android.codelabs.paging

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.android.codelabs.paging.api.GithubService
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.ui.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
object Injection {

    private fun provideGithubRepository(): GithubRepository {
        return GithubRepository(GithubService.create())
    }

    fun provideViewModelFactory(owner: SavedStateRegistryOwner, application: Application): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideGithubRepository(), application)
    }

}
