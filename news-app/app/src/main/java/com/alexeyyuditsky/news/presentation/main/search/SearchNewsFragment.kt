package com.alexeyyuditsky.news.presentation.main.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.alexeyyuditsky.news.R
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.databinding.FragmentSearchNewsBinding
import com.alexeyyuditsky.news.presentation.base.BaseFragment
import com.alexeyyuditsky.news.Injection
import com.alexeyyuditsky.news.presentation.base.Const
import com.alexeyyuditsky.news.presentation.base.viewModelCreator
import com.alexeyyuditsky.news.presentation.main.adapters.ArticlesAdapter
import com.alexeyyuditsky.news.presentation.main.adapters.ReposLoadStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class SearchNewsFragment : BaseFragment(R.layout.fragment_search_news) {

    override val viewModel by viewModelCreator { SearchViewModel(Injection.pagingNewsRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchNewsBinding.bind(view)

        binding.bindState(
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
    }

    private fun FragmentSearchNewsBinding.bindState(
        pagingData: Flow<PagingData<Article>>,
        uiActions: (String) -> Unit
    ) {
        val newsListener = { article: Article ->
            val bundle = bundleOf(Const.ARTICLE to article)
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment, bundle)
        }
        val articlesAdapter = ArticlesAdapter(newsListener)

        bindSearch(
            onQueryChanged = uiActions
        )
        bindList(
            articlesAdapter = articlesAdapter,
            pagingData = pagingData
        )
    }

    private fun FragmentSearchNewsBinding.bindSearch(
        onQueryChanged: (String) -> Unit
    ) {
        searchEditText.addTextChangedListener {
            updateRepoListFromInput(onQueryChanged)
        }
    }

    private fun FragmentSearchNewsBinding.updateRepoListFromInput(onQueryChanged: (String) -> Unit) {
        onQueryChanged(searchEditText.text!!.trim().toString())
    }

    private fun FragmentSearchNewsBinding.bindList(
        articlesAdapter: ArticlesAdapter,
        pagingData: Flow<PagingData<Article>>
    ) {
        recyclerView.adapter = articlesAdapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { articlesAdapter.retry() },
            footer = ReposLoadStateAdapter { articlesAdapter.retry() }
        )

        recyclerView.addItemDecoration(DividerItemDecoration(root.context, DividerItemDecoration.VERTICAL))
        retryButton.setOnClickListener { articlesAdapter.retry() }

        lifecycleScope.launch {
            pagingData.collectLatest {
                articlesAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            articlesAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && articlesAdapter.itemCount == 0
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