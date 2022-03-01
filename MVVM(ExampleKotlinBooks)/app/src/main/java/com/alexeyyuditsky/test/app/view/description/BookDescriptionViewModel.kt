package com.alexeyyuditsky.test.app.view.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.model.BooksRepository
import com.alexeyyuditsky.test.app.view.edit.EditBookFragment
import com.alexeyyuditsky.test.foundation.model.PendingResult
import com.alexeyyuditsky.test.foundation.model.SuccessResult
import com.alexeyyuditsky.test.foundation.model.takeSuccess
import com.alexeyyuditsky.test.foundation.sideeffects.navigator.Navigator
import com.alexeyyuditsky.test.foundation.sideeffects.resources.Resources
import com.alexeyyuditsky.test.foundation.sideeffects.toasts.Toasts
import com.alexeyyuditsky.test.foundation.uiactions.UIActions
import com.alexeyyuditsky.test.foundation.views.BaseViewModel
import com.alexeyyuditsky.test.foundation.views.LiveResult
import com.alexeyyuditsky.test.foundation.views.MutableLiveResult

class BookDescriptionViewModel(
    private val screen: BookDescriptionFragment.Screen,
    private val navigator: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    private val booksRepository: BooksRepository,
) : BaseViewModel() {

    private val _book = MutableLiveResult<Book>(PendingResult())
    val book: LiveResult<Book> = _book

    val screenTitle: LiveData<String> = Transformations.map(_book) { result ->
        if (result is SuccessResult) {
            resources.getString(R.string.view_model_description_fragment_title, result.data.name)
        } else {
            resources.getString(R.string.book)
        }
    }

    init {
        load()
    }

    fun onOpenScreenForEditing() {
        val book = _book.value.takeSuccess() ?: return
        val screen = EditBookFragment.Screen(book.id)
        navigator.launch(screen)
    }

    override fun onResult(result: Any) {
        if (result is Book) {
            _book.postValue(SuccessResult(result))
            val message = resources.getString(R.string.changes_saved)
            toasts.toast(message)
        }
    }

    fun tryAgain() {
        _book.value = PendingResult()
        load()
    }

    private fun load() {
        into(_book) { booksRepository.getById(screen.bookId) }
    }

}