package com.example.android.codelabs.paging.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.android.codelabs.paging.databinding.RepoViewItemBinding
import com.example.android.codelabs.paging.model.Repo

class ReposAdapter : PagingDataAdapter<Repo, RepoViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(RepoViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repoItem = getItem(position) ?: return
        holder.bind(repoItem)
    }

    private companion object {
        val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem.fullName == newItem.fullName
            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem == newItem
        }
    }

}
