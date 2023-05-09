package com.example.android.codelabs.paging.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.android.codelabs.paging.Injection
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.databinding.ActivitySearchRepositoriesBinding
import com.example.android.codelabs.paging.log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@FlowPreview
@ExperimentalCoroutinesApi
class RepositoriesActivity : AppCompatActivity() {

    private val viewModel by viewModels<RepositoriesViewModel> { Injection.provideViewModelFactory(this, application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bindState(
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
    }

    private fun ActivitySearchRepositoriesBinding.bindState(
        pagingData: Flow<PagingData<UiModel>>, uiActions: (String) -> Unit
    ) {
        bindSearch(onQueryChanged = uiActions)
        bindList(pagingData = pagingData)
    }

    private fun ActivitySearchRepositoriesBinding.bindSearch(
        onQueryChanged: (String) -> Unit
    ) {
        searchEditText.addTextChangedListener {
            updateRepoListFromInput(onQueryChanged)
        }
    }

    private fun ActivitySearchRepositoriesBinding.updateRepoListFromInput(onQueryChanged: (String) -> Unit) {
        onQueryChanged(searchEditText.text!!.trim().toString())
        recyclerView.scrollToPosition(0)
    }

    private fun ActivitySearchRepositoriesBinding.bindList(
        pagingData: Flow<PagingData<UiModel>>
    ) {
        val repoAdapter = ReposAdapter()
        val header = ReposLoadStateAdapter { repoAdapter.retry() }
        val footer = ReposLoadStateAdapter { repoAdapter.retry() }

        recyclerView.adapter = repoAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = footer
        )

        val divider = DividerItemDecoration(this@RepositoriesActivity, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(divider)

        retryButton.setOnClickListener { repoAdapter.retry() }

        lifecycleScope.launch {
            pagingData.collectLatest {
                repoAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            repoAdapter.loadStateFlow.collect { loadState ->
                header.loadState = loadState.mediator?.refresh
                    ?.takeIf { it is LoadState.Error && repoAdapter.itemCount > 0 }
                    ?: loadState.prepend

                val isListEmpty = loadState.refresh is LoadState.NotLoading && repoAdapter.itemCount == 0

                noResultTextView.isVisible = isListEmpty
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                        || loadState.mediator?.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error && repoAdapter.itemCount == 0

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error ?: loadState.prepend as? LoadState.Error

                errorState?.let {
                    Toast.makeText(this@RepositoriesActivity, getString(R.string.whoops, it.error), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

}