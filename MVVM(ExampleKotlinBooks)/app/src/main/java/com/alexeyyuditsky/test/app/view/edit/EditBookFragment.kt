package com.alexeyyuditsky.test.app.view.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.databinding.FragmentEditBinding
import com.alexeyyuditsky.test.foundation.views.*
import com.bumptech.glide.Glide

class EditBookFragment : BaseFragment(), HasCustomAction, HasScreenTitle {

    class Screen(val id: Long) : BaseScreen()

    override val viewModel by screenViewModel<EditBookViewModel>()

    private lateinit var binding: FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)

        viewModel.book.observe(viewLifecycleOwner) {
            initViews(it)
        }

        binding.saveButton.setOnClickListener {
            val editBook = createEditBook()
            viewModel.onSavePressed(editBook)
        }

        return binding.root
    }

    private fun initViews(book: Book) = binding.apply {
        Glide.with(bookImageView.context)
            .load(book.image)
            .into(bookImageView)
        nameTextView.setText(book.name)
        authorTextView.setText(book.authors)
        yearTextView.setText(book.year)
        descriptionTextView.setText(book.description)
    }

    private fun createEditBook(): Book {
        val currentBook = viewModel.book.value!!
        return currentBook.copy(
            name = binding.nameTextView.text.toString(),
            authors = binding.authorTextView.text.toString(),
            year = binding.yearTextView.text.toString(),
            description = binding.descriptionTextView.text.toString()
        )
    }

    override fun getCustomAction(): CustomAction = CustomAction(
        iconRes = R.drawable.ic_done,
        textRes = R.string.done,
        onCustomAction = Runnable {
            val editBook = createEditBook()
            viewModel.onGoToMainPressed(editBook)
        }
    )

    override fun getScreenTitle(): String {
        return getString(R.string.edit_fragment_title, viewModel.book.value?.name)
    }

}