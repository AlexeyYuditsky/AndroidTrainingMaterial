package com.example.android.codelabs.paging.ui

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.databinding.ReposLoadStateFooterViewItemBinding

class ReposLoadStateViewHolder(
    private val binding: ReposLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        with(binding) {
            if (loadState is LoadState.Error) {
                errorTextView.text = loadState.error.localizedMessage
            }
            retryButton.isVisible = loadState is LoadState.Error
            errorTextView.isVisible = loadState is LoadState.Error
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }

}