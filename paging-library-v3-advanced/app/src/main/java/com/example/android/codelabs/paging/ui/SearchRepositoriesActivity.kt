package com.example.android.codelabs.paging.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.flatMap
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.android.codelabs.paging.Injection
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.databinding.ActivitySearchRepositoriesBinding
import com.example.android.codelabs.paging.log
import com.example.android.codelabs.paging.model.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class SearchRepositoriesActivity : AppCompatActivity() {

    private val viewModel by viewModels<SearchRepositoriesViewModel> { Injection.provideViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
    }

    private fun ActivitySearchRepositoriesBinding.bindState(
        uiState: StateFlow<UiState>, // UiState(query = Android(default value))
        pagingData: Flow<PagingData<Repo>>, // List<Repo>
        uiActions: (UiState) -> Unit
    ) {
        val repoAdapter = ReposAdapter()

        bindSearch(
            uiState = uiState,
            onQueryChanged = uiActions
        )
        bindList(
            repoAdapter = repoAdapter,
            pagingData = pagingData
        )
    }

    private fun ActivitySearchRepositoriesBinding.bindSearch(
        uiState: StateFlow<UiState>,
        onQueryChanged: (UiState) -> Unit
    ) {
        searchEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            uiState.collect {
                searchEditText.setText(it.query)
            }
        }
    }

    private fun ActivitySearchRepositoriesBinding.updateRepoListFromInput(onQueryChanged: (UiState) -> Unit) {
        searchEditText.text!!.trim().let {
            if (it.isNotBlank()) {
                onQueryChanged(UiState(query = it.toString()))
            }
        }
    }

    private fun ActivitySearchRepositoriesBinding.bindList(
        repoAdapter: ReposAdapter,
        pagingData: Flow<PagingData<Repo>>
    ) {
        recyclerView.adapter = repoAdapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { repoAdapter.retry() },
            footer = ReposLoadStateAdapter { repoAdapter.retry() }
        )

        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))

        lifecycleScope.launch {
            pagingData.collectLatest {
                repoAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            repoAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && repoAdapter.itemCount == 0
                noResultTextView.isVisible = isListEmpty
                recyclerView.isVisible = !isListEmpty
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                retryButton.isVisible = loadState.source.refresh is LoadState.Error

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(recyclerView.context, getString(R.string.whoops, it.error), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}