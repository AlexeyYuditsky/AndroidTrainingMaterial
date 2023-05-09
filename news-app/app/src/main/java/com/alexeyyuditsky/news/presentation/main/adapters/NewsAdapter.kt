package com.alexeyyuditsky.news.presentation.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.databinding.ArticleViewholderBinding

class NewsAdapter(
    private val newsListener: (Article) -> Unit
) : RecyclerView.Adapter<ArticleViewHolder>() {

    val differ = AsyncListDiffer(this, ArticleDiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding, newsListener)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position] ?: return
        holder.bind(article)
    }

    override fun getItemCount(): Int = differ.currentList.size

}