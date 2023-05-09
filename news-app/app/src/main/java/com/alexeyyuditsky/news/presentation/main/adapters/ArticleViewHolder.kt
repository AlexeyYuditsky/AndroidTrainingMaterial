package com.alexeyyuditsky.news.presentation.main.adapters

import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.databinding.ArticleViewholderBinding
import com.bumptech.glide.Glide

class ArticleViewHolder(
    private val binding: ArticleViewholderBinding,
    private val newsListener: (Article) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) = with(binding) {
        Glide.with(root).load(article.urlToImage).into(ivArticleImage)
        tvSource.text = article.source?.name
        tvTitle.text = article.title
        tvDescription.text = article.description
        tvPublishedAt.text = article.publishedAt

        root.tag = article
        root.setOnClickListener {
            val art = it.tag as Article
            newsListener(art)
        }
    }

}