package com.alexeyyuditsky.test.app.view.books

import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.model.BooksListener
import com.alexeyyuditsky.test.app.model.BooksRepository
import com.alexeyyuditsky.test.app.view.description.BookDescriptionFragment
import com.alexeyyuditsky.test.foundation.model.PendingResult
import com.alexeyyuditsky.test.foundation.model.SuccessResult
import com.alexeyyuditsky.test.foundation.navigator.Navigator
import com.alexeyyuditsky.test.foundation.views.BaseViewModel
import com.alexeyyuditsky.test.foundation.views.LiveResult
import com.alexeyyuditsky.test.foundation.views.MutableLiveResult

class BooksListViewModel(
    private val navigator: Navigator,
    private val booksRepository: BooksRepository,
) : BaseViewModel(), BooksAdapter.Listener {

    private val _booksList = MutableLiveResult<List<Book>>(PendingResult())
    val booksList: LiveResult<List<Book>> = _booksList

    private val bookListener: BooksListener = {
        _booksList.postValue(SuccessResult(it))
    }

    init {
        booksRepository.addListener(bookListener)
        load()
    }

    override fun onBookChosen(book: Book) {
        navigator.launch(BookDescriptionFragment.Screen(bookId = book.id))
    }

    fun tryAgain() {
        _booksList.postValue(PendingResult())
        booksRepository.addListener(bookListener)
    }

    private fun load() {
        booksRepository.getBooks().into(_booksList)
    }

}