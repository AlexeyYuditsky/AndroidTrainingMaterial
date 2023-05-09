package com.alexeyyuditsky.news.presentation.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.alexeyyuditsky.news.databinding.ReposLoadStateFooterViewItemBinding

class ReposLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ReposLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ReposLoadStateViewHolder {
        val binding = ReposLoadStateFooterViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReposLoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}