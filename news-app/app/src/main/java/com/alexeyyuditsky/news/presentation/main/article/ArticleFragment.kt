package com.alexeyyuditsky.news.presentation.main.article

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.alexeyyuditsky.news.R
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.databinding.FragmentArticleBinding
import com.alexeyyuditsky.news.presentation.base.BaseFragment
import com.alexeyyuditsky.news.Injection
import com.alexeyyuditsky.news.presentation.base.viewModelCreator
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    override val viewModel by viewModelCreator { ArticleViewModel(Injection.newsRepository) }
    private lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)
        val args by navArgs<ArticleFragmentArgs>()
        launchWebView(args.article)
        setFabListener(args.article)
    }

    private fun launchWebView(article: Article) = binding.webView.apply {
        webViewClient = WebViewClient()
        loadUrl(article.url!!)
    }

    private fun setFabListener(article: Article) = binding.fab.setOnClickListener {
        viewModel.saveArticle(article)
        Snackbar.make(it, getString(R.string.article_saved), Snackbar.LENGTH_SHORT).show()
    }

}