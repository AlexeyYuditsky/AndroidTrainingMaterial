package com.example.android.codelabs.paging

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.savedstate.SavedStateRegistryOwner
import com.example.android.codelabs.paging.api.GithubService
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.db.RepoDatabase
import com.example.android.codelabs.paging.ui.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalPagingApi
@FlowPreview
@ExperimentalCoroutinesApi
object Injection {

    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(GithubService.create(), RepoDatabase.getInstance(context))
    }

    fun provideViewModelFactory(owner: SavedStateRegistryOwner, application: Application): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideGithubRepository(application), application)
    }

}
