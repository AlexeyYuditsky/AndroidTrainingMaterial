package com.alexeyyuditsky.test.app.view.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.databinding.FragmentDescriptionBinding
import com.alexeyyuditsky.test.foundation.views.BaseFragment
import com.alexeyyuditsky.test.foundation.views.BaseScreen
import com.alexeyyuditsky.test.foundation.views.screenViewModel
import com.bumptech.glide.Glide

class BookDescriptionFragment : BaseFragment() {

    class Screen(val bookId: Long) : BaseScreen()

    override val viewModel by screenViewModel<BookDescriptionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentDescriptionBinding.inflate(inflater, container, false)

        viewModel.book.observe(viewLifecycleOwner) {
            initViews(binding, it)
        }

        binding.openScreenButton.setOnClickListener {
            viewModel.onOpenScreenPressed()
        }

        return binding.root
    }

    private fun initViews(binding: FragmentDescriptionBinding, book: Book) {
        binding.apply {
            Glide.with(bookImageView.context)
                .load(book.image)
                .into(bookImageView)
            nameTextView.text = book.name
            authorTextView.text = book.authors
            yearTextView.text = book.year
            descriptionTextView.text = book.description
        }
    }

}