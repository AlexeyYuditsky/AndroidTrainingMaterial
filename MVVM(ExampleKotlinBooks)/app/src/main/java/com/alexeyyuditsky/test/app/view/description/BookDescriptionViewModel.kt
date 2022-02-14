package com.alexeyyuditsky.test.app.view.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.model.BooksRepository
import com.alexeyyuditsky.test.app.view.edit.EditBookFragment
import com.alexeyyuditsky.test.foundation.navigator.Navigator
import com.alexeyyuditsky.test.foundation.uiactions.UIActions
import com.alexeyyuditsky.test.foundation.views.BaseViewModel

class BookDescriptionViewModel(
    screen: BookDescriptionFragment.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val booksRepository: BooksRepository,
) : BaseViewModel() {

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> = _book

    init {
        _book.value = booksRepository.getById(screen.bookId)
    }

    fun onOpenScreenPressed() {
        val bookId = _book.value?.id!!
        navigator.launch(EditBookFragment.Screen(id = bookId))
    }

    override fun onResult(result: Any) {
        if (result is Book) {
            _book.value = booksRepository.getById(result.id)
        }
    }

}