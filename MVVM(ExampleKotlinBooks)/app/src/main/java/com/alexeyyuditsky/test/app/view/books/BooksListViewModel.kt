package com.alexeyyuditsky.test.app.view.books

import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            delay(300)
            booksRepository.addListener(bookListener)
        }
    }

    override fun onBookChosen(book: Book) {
        navigator.launch(BookDescriptionFragment.Screen(bookId = book.id))
    }

    fun tryAgain() {
        viewModelScope.launch {
            _booksList.postValue(PendingResult())
            delay(1000)
            booksRepository.addListener(bookListener)
        }
    }

}