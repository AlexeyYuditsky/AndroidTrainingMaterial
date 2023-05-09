package com.alexeyyuditsky.news.presentation.main.saved

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.news.R
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.databinding.FragmentSavedNewsBinding
import com.alexeyyuditsky.news.presentation.base.BaseFragment
import com.alexeyyuditsky.news.presentation.base.Const
import com.alexeyyuditsky.news.Injection
import com.alexeyyuditsky.news.presentation.base.viewModelCreator
import com.alexeyyuditsky.news.presentation.main.adapters.NewsAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SavedNewsFragment : BaseFragment(R.layout.fragment_saved_news) {

    override val viewModel by viewModelCreator { SavedViewModel(Injection.newsRepository) }
    private lateinit var binding: FragmentSavedNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)
        val newsAdapter = createNewsAdapter()
        createRecyclerView(newsAdapter)
        createNewsSwiper(newsAdapter)
        observeSavedNews(newsAdapter)
    }

    private fun createNewsAdapter(): NewsAdapter {
        val newsListener = { article: Article ->
            val bundle = bundleOf(Const.ARTICLE to article)
            findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment, bundle)
        }
        return NewsAdapter(newsListener)
    }

    private fun createNewsSwiper(newsAdapter: NewsAdapter) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(requireView(), getString(R.string.successfully_deleted), Snackbar.LENGTH_SHORT).apply {
                    setAction(getString(R.string.undo)) {
                        viewModel.saveArticle(article)
                    }
                }.show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }

    private fun observeSavedNews(newsAdapter: NewsAdapter) = lifecycleScope.launch {
        viewModel.getSavedNews().collect { articles ->
            newsAdapter.differ.submitList(articles)
        }
    }

    private fun createRecyclerView(newsAdapter: NewsAdapter) = binding.rvSavedNews.apply {
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(activity)
    }

}