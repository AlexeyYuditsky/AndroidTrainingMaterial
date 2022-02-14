package com.alexeyyuditsky.test.app.view.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.model.BooksRepository
import com.alexeyyuditsky.test.foundation.navigator.Navigator
import com.alexeyyuditsky.test.foundation.uiactions.UIActions
import com.alexeyyuditsky.test.foundation.views.BaseViewModel

class EditBookViewModel(
    screen: EditBookFragment.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val booksRepository: BooksRepository,
) : BaseViewModel() {

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> = _book

    init {
        _book.value = booksRepository.getById(screen.id)
    }

    fun onSavePressed(book: Book) {
        booksRepository.changeBook(book)
        navigator.goBack(book)
    }

    fun onGoToMainPressed() {
        navigator.goToMain()
    }

}