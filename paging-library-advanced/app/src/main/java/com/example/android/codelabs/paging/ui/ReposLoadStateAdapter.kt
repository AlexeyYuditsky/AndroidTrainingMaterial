package com.example.android.codelabs.paging.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.android.codelabs.paging.databinding.ReposLoadStateFooterViewItemBinding
import com.google.android.material.progressindicator.LinearProgressIndicator

class ReposLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ReposLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ReposLoadStateViewHolder {
        return ReposLoadStateViewHolder(
            ReposLoadStateFooterViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )
    }

    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}