package com.example.android.codelabs.paging.ui

import androidx.recyclerview.widget.DiffUtil

object UiModelDiffCallback : DiffUtil.ItemCallback<UiModel>() {
    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean =
        ((oldItem is UiModel.RepoItem) && (newItem is UiModel.RepoItem) && (oldItem.repo.fullName == newItem.repo.fullName)) ||
                ((oldItem is UiModel.SeparatorItem) && (newItem is UiModel.SeparatorItem) && (oldItem.description == newItem.description))

    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean = oldItem == newItem
}