package com.example.android.codelabs.paging.ui

import android.content.Intent
import android.net.Uri
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.databinding.RepoViewItemBinding
import com.example.android.codelabs.paging.model.Repo

class RepoViewHolder(
    private val binding: RepoViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repo: Repo) = with(binding) {
        nameTextView.text = repo.fullName
        starsTextView.text = repo.stars.toString()
        forksTextView.text = repo.forks.toString()

        if (!repo.description.isNullOrBlank()) {
            descriptionTextView.text = repo.description
            descriptionTextView.isVisible = true
        } else {
            descriptionTextView.isGone = true
        }

        if (!repo.language.isNullOrBlank()) {
            languageTextView.text =
                binding.root.context.resources.getString(R.string.language, repo.language)
            languageTextView.isVisible = true
        } else {
            languageTextView.isGone = true
        }

        if (!repo.url.isNullOrBlank()) {
            root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.url))
                root.context.startActivity(intent)
            }
        }
    }

}
