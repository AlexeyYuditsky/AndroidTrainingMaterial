package com.alexeyyuditsky.test.app.view.books

import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.model.BooksListener
import com.alexeyyuditsky.test.app.model.BooksRepository
import com.alexeyyuditsky.test.app.view.description.BookDescriptionFragment
import com.alexeyyuditsky.test.foundation.model.PendingResult
import com.alexeyyuditsky.test.foundation.model.SuccessResult
import com.alexeyyuditsky.test.foundation.sideeffects.navigator.Navigator
import com.alexeyyuditsky.test.foundation.sideeffects.toasts.Toasts
import com.alexeyyuditsky.test.foundation.views.BaseViewModel
import com.alexeyyuditsky.test.foundation.views.LiveResult
import com.alexeyyuditsky.test.foundation.views.MutableLiveResult
import com.alexeyyuditsky.test.foundation.sideeffects.resources.Resources

class BooksListViewModel(
    private val navigator: Navigator,
    private val booksRepository: BooksRepository,
    private val toasts: Toasts,
    private val resources: Resources,
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

    override fun onCleared() {
        super.onCleared()
        booksRepository.removeListener(bookListener)
    }

    override fun onChooseBook(book: Book) {
        val screen = BookDescriptionFragment.Screen(book.id)
        navigator.launch(screen)
    }

    override fun onResult(result: Any) {
        if (result is Book) {
            val message = resources.getString(R.string.changes_saved_with_name_book, result.name)
            toasts.toast(message)
        }
    }

    fun tryAgain() {
        _booksList.value = PendingResult()
        load()
    }

    private fun load() {
        into(_booksList) { booksRepository.getBooks() }
    }

}