package com.example.android.codelabs.paging.data

import androidx.paging.*
import com.example.android.codelabs.paging.api.GithubService
import com.example.android.codelabs.paging.db.RepoDatabase
import com.example.android.codelabs.paging.log
import com.example.android.codelabs.paging.model.Repo
import kotlinx.coroutines.flow.Flow

const val NETWORK_PAGE_SIZE = 2

@ExperimentalPagingApi
class GithubRepository(
    private val service: GithubService,
    private val database: RepoDatabase
) {

    fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        val dbQuery = "%$query%"
        val pagingSourceFactory: (() -> PagingSource<Int, Repo>) = { database.reposDao().reposByName(dbQuery) }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GithubRemoteMediator(
                query = query,
                service = service,
                repoDatabase = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}
