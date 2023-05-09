package com.alexeyyuditsky.news.presentation.main.breaking

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.news.Injection
import com.alexeyyuditsky.news.R
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.databinding.FragmentBreakingNewsBinding
import com.alexeyyuditsky.news.presentation.base.BaseFragment
import com.alexeyyuditsky.news.presentation.base.Const
import com.alexeyyuditsky.news.presentation.base.Result
import com.alexeyyuditsky.news.presentation.base.viewModelCreator
import com.alexeyyuditsky.news.presentation.main.adapters.NewsAdapter
import kotlinx.coroutines.launch

class BreakingNewsFragment : BaseFragment(R.layout.fragment_breaking_news) {

    override val viewModel by viewModelCreator { BreakingViewModel(Injection.newsRepository) }
    private lateinit var binding: FragmentBreakingNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreakingNewsBinding.bind(view)
        val newsAdapter = createNewsAdapter()
        createRecyclerView(newsAdapter)
        observeBreakingNews(newsAdapter)
    }

    private fun createNewsAdapter(): NewsAdapter {
        val newsListener = { article: Article ->
            val bundle = bundleOf(Const.ARTICLE to article)
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment, bundle)
        }
        return NewsAdapter(newsListener)
    }

    private fun createRecyclerView(newsAdapter: NewsAdapter) = binding.recyclerView.apply {
        adapter = newsAdapter
        addOnScrollListener(this@BreakingNewsFragment.scrollListener)
    }

    private fun observeBreakingNews(newsAdapter: NewsAdapter) = lifecycleScope.launch {
        viewModel.breakingNews.collect { response ->
            when (response) {
                is Result.Success -> {
                    hideProgressBar()
                    newsAdapter.differ.submitList(response.data.articles.toList())
                    val totalPages = response.data.totalResults / Const.QUERY_PAGE_SIZE + 2
                    isLastPage = viewModel.breakingNewsPage == totalPages
                    if (isLastPage) {
                        binding.recyclerView.setPadding(0, 0, 0, 0)
                    }
                }
                is Result.Error -> {
                    hideProgressBar()
                    Toast.makeText(activity, "An error occurred: ${response.error}", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showProgressBar()
                }
                is Result.Empty -> {}
            }
        }
    }


    private fun hideProgressBar() {
        binding.progressBar.isVisible = false
        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Const.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem &&
                    isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getBreakingNews()
                isScrolling = false
            }
        }
    }

}