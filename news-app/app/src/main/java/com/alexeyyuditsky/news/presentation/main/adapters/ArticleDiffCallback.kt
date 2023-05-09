package com.alexeyyuditsky.news.presentation.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.alexeyyuditsky.news.data.retrofit.Article

object ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem.url == newItem.url
    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem == newItem
}