package com.alexeyyuditsky.test.app.view.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.model.BooksListener
import com.alexeyyuditsky.test.app.model.BooksRepository
import com.alexeyyuditsky.test.app.view.description.BookDescriptionFragment
import com.alexeyyuditsky.test.foundation.navigator.Navigator
import com.alexeyyuditsky.test.foundation.views.BaseViewModel

class BooksListViewModel(
    private val navigator: Navigator,
    booksRepository: BooksRepository,
) : BaseViewModel(), BooksAdapter.Listener {

    private val _booksList = MutableLiveData<List<Book>>()
    val booksList: LiveData<List<Book>> = _booksList

    private val listener: BooksListener = {
        _booksList.value = it
    }

    init {
        booksRepository.addListener(listener)
    }

    override fun onBookChosen(book: Book) {
        navigator.launch(BookDescriptionFragment.Screen(bookId = book.id))
    }

}