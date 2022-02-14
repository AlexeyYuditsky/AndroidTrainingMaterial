package com.alexeyyuditsky.test.app.view.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.databinding.FragmentDescriptionBinding
import com.alexeyyuditsky.test.databinding.FragmentEditBinding
import com.alexeyyuditsky.test.foundation.views.BaseFragment
import com.alexeyyuditsky.test.foundation.views.BaseScreen
import com.alexeyyuditsky.test.foundation.views.screenViewModel
import com.bumptech.glide.Glide

class EditBookFragment : BaseFragment() {

    class Screen(val id: Long) : BaseScreen()

    override val viewModel by screenViewModel<EditBookViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentEditBinding.inflate(inflater, container, false)

        viewModel.book.observe(viewLifecycleOwner) {
            initViews(binding, it)
        }

        binding.saveButton.setOnClickListener {
            val currentBook = viewModel.book.value!!
            val editBook = currentBook.copy(
                name = binding.nameTextView.text.toString(),
                authors = binding.authorTextView.text.toString(),
                year = binding.yearTextView.text.toString(),
                description = binding.descriptionTextView.text.toString()
            )
            viewModel.onSavePressed(editBook)
        }

        binding.goToMainButton.setOnClickListener {
            viewModel.onGoToMainPressed()
        }

        return binding.root
    }

    private fun initViews(binding: FragmentEditBinding, book: Book) {
        binding.apply {
            Glide.with(bookImageView.context)
                .load(book.image)
                .into(bookImageView)
            nameTextView.setText(book.name)
            authorTextView.setText(book.authors)
            yearTextView.setText(book.year)
            descriptionTextView.setText(book.description)
        }
    }

}