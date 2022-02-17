package com.alexeyyuditsky.test.app.view.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.model.BooksRepository
import com.alexeyyuditsky.test.app.view.edit.EditBookFragment
import com.alexeyyuditsky.test.foundation.model.ErrorResult
import com.alexeyyuditsky.test.foundation.model.PendingResult
import com.alexeyyuditsky.test.foundation.model.SuccessResult
import com.alexeyyuditsky.test.foundation.model.takeSuccess
import com.alexeyyuditsky.test.foundation.navigator.Navigator
import com.alexeyyuditsky.test.foundation.uiactions.UIActions
import com.alexeyyuditsky.test.foundation.views.BaseViewModel
import com.alexeyyuditsky.test.foundation.views.LiveResult
import com.alexeyyuditsky.test.foundation.views.MutableLiveResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookDescriptionViewModel(
    private val screen: BookDescriptionFragment.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val booksRepository: BooksRepository,
) : BaseViewModel() {

    private val _book = MutableLiveResult<Book>(PendingResult())
    val book: LiveResult<Book> = _book

    val screenTitle: LiveData<String> = Transformations.map(_book) { result ->
        if (result is SuccessResult) {
            uiActions.getString(R.string.view_model_description_fragment_title, result.data.name)
        } else {
            uiActions.getString(R.string.book)
        }
    }

    init {
        viewModelScope.launch {
            delay(1000)
            _book.value = ErrorResult(RuntimeException())
            // _book.value = SuccessResult(booksRepository.getById(screen.bookId))
        }
    }

    fun onOpenScreenPressed() {
        val book = _book.value.takeSuccess() ?: return
        navigator.launch(EditBookFragment.Screen(bookId = book.id))
    }

    override fun onResult(result: Any) {
        if (result is Book) {
            _book.value = SuccessResult(booksRepository.getById(result.id))
            val message = uiActions.getString(R.string.changes_saved)
            uiActions.toast(message)
        }
    }

    fun tryAgain() {
        viewModelScope.launch {
            _book.value = PendingResult()
            delay(1000)
            _book.value = SuccessResult(booksRepository.getById(screen.bookId))
        }
    }

}