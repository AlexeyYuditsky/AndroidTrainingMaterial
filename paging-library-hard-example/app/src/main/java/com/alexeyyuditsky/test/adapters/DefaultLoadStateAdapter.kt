package com.alexeyyuditsky.test.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.test.databinding.PartDefaultLoadStateBinding

typealias TryAgainAction = () -> Unit

class DefaultLoadStateAdapter(
    private val tryAgainAction: TryAgainAction
) : LoadStateAdapter<DefaultLoadStateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val binding = PartDefaultLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, tryAgainAction)
    }

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class Holder(
        private val binding: PartDefaultLoadStateBinding,
        private val tryAgainAction: TryAgainAction
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tryAgainButton.setOnClickListener { tryAgainAction() }
        }

        fun bind(loadState: LoadState) = binding.apply {
            messageTextView.isVisible = loadState is LoadState.Error
            tryAgainButton.isVisible = loadState is LoadState.Error
            progressBar.isVisible = loadState is LoadState.Loading
        }

    }

}