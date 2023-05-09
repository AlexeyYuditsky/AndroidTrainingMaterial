package com.example.android.codelabs.paging.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.databinding.SeparatorViewItemBinding

class SeparatorViewHolder(
    private val binding: SeparatorViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(separatorText: String) = with(binding) {
        separatorTextView.text = separatorText
    }

}