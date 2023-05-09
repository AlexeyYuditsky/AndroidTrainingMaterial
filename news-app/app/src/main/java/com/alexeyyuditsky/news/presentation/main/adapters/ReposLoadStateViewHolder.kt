package com.alexeyyuditsky.news.presentation.main.adapters

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.news.databinding.ReposLoadStateFooterViewItemBinding

class ReposLoadStateViewHolder(
    private val binding: ReposLoadStateFooterViewItemBinding,
    retry: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) = with(binding) {
        if (loadState is LoadState.Error) {
            errorTextView.text = loadState.error.localizedMessage
        }
        errorTextView.isVisible = loadState is LoadState.Error
        retryButton.isVisible = loadState is LoadState.Error
        progressBar.isVisible = loadState is LoadState.Loading
    }

}