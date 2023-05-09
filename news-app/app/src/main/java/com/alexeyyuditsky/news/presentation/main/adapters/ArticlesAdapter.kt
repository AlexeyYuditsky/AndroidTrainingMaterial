package com.alexeyyuditsky.news.presentation.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.databinding.ArticleViewholderBinding

class ArticlesAdapter(
    private val newsListener: (Article) -> Unit
) : PagingDataAdapter<Article, ArticleViewHolder>(ArticleDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding, newsListener)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position) ?: return
        holder.bind(article)
    }

}