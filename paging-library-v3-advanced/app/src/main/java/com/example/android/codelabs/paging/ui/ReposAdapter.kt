package com.example.android.codelabs.paging.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.databinding.RepoViewItemBinding
import com.example.android.codelabs.paging.databinding.SeparatorViewItemBinding
import com.example.android.codelabs.paging.log

class ReposAdapter : PagingDataAdapter<UiModel, ViewHolder>(UiModelDiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.RepoItem -> R.layout.repo_view_item
            is UiModel.SeparatorItem -> R.layout.separator_view_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == R.layout.repo_view_item) {
            val binding = RepoViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            RepoViewHolder(binding)
        } else {
            val binding = SeparatorViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SeparatorViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uiModel = getItem(position) ?: return
        when (uiModel) {
            is UiModel.RepoItem -> (holder as RepoViewHolder).bind(uiModel.repo)
            is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModel.description)
        }
    }

}