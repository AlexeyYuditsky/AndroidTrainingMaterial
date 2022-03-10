package com.alexeyyuditsky.test.app.view.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.view.collectFlow
import com.alexeyyuditsky.test.app.view.edit.EditBookViewModel.*
import com.alexeyyuditsky.test.app.view.onTryAgain
import com.alexeyyuditsky.test.app.view.renderSimpleResult
import com.alexeyyuditsky.test.databinding.FragmentEditBinding
import com.alexeyyuditsky.test.foundation.views.*
import com.bumptech.glide.Glide

class EditBookFragment : BaseFragment(), HasCustomAction, HasScreenTitle {

    class Screen(val bookId: Long) : BaseScreen()

    private lateinit var binding: FragmentEditBinding

    private lateinit var currentBook: Book

    override val viewModel by screenViewModel<EditBookViewModel>()

    override fun getScreenTitle(): String? = viewModel.screenTitle.value

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)

        viewModel.screenTitle.observe(viewLifecycleOwner) {
            notifyScreenUpdates()
        }

        collectFlow(viewModel.viewState) { result ->
            renderSimpleResult(binding.root, result) { viewState ->
                initViews(binding, viewState)
            }
        }

        binding.saveResultButton.setOnClickListener {
            val editBook = createEditBook()
            viewModel.onSavePressed(editBook)
        }

        onTryAgain(binding.root) {
            viewModel.tryAgain()
        }

        return binding.root
    }

    private fun initViews(binding: FragmentEditBinding, viewState: ViewState) = binding.apply {
        Glide.with(bookImageView.context)
            .load(viewState.book.image)
            .into(bookImageView)
        nameTextView.setText(viewState.book.name)
        authorTextView.setText(viewState.book.authors)
        yearTextView.setText(viewState.book.year)
        descriptionTextView.setText(viewState.book.description)
        saveResultButton.isVisible = viewState.showSaveButton
        linearContainer.isVisible = viewState.showSaveButton

        saveProgressGroup.isVisible = viewState.showSaveProgressBar
        saveProgressBar.progress = viewState.saveProgressPercentage
        savingPercentageTextView.text = viewState.saveProgressPercentageMessage

        currentBook = viewState.book
    }

    private fun createEditBook(): Book {
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
            viewModel.onSavePressed(editBook, goToMainScreen = true)
        }
    )

}